# Eclipse MAT 堆分析实操手册

本手册结合仓库中的 `JmapHeapDemo` + `jmap` 堆转储，**已在本机（MAT 1.16.1 + JDK 21）实际跑通**。

---

## 一、MAT 能干什么

| 能力 | 说明 |
|------|------|
| **Leak Suspects** | 自动猜测泄漏嫌疑（Problem Suspect） |
| **Dominator Tree** | 看谁「支配」最多堆内存（Retained Heap） |
| **Histogram** | 按类统计对象数 / Shallow / Retained |
| **Path to GC Roots** | 从对象反查 GC Root，确认是否泄漏 |

jmap `-histo` 只能看排行；**MAT 能追引用链、算 Retained Size**。

---

## 二、安装 MAT（macOS）

### 方式 1：Homebrew（推荐）

```bash
# 国内下载 Homebrew cask 通常需要代理
export http_proxy=http://localhost:7890 https_proxy=http://localhost:7890
brew install --cask memoryanalyzer
```

安装位置：`/Applications/MemoryAnalyzer.app`

### 方式 2：官网下载

https://eclipse.dev/mat/downloads.php — 选与芯片匹配的版本（Intel = x86_64，Apple Silicon = AArch64）。

### 配置 JDK（必做）

MAT 1.16 **要求 Java 17+**。若启动报 `Unable to locate a Java Runtime`：

```bash
./mat/setup-mat-jdk.sh
```

或手动编辑 `/Applications/MemoryAnalyzer.app/Contents/Eclipse/MemoryAnalyzer.ini`，在 `-vmargs` **之前**加入：

```ini
-vm
/Users/weli/.sdkman/candidates/java/current/bin/java
```

验证：

```bash
open /Applications/MemoryAnalyzer.app
# Help → About Eclipse Memory Analyzer → Installation Details 中应看到 Java 21
```

大堆转储可调大 MAT 内存：把 `-Xmx1024m` 改为 `-Xmx4g`（需小于物理内存）。

---

## 三、准备堆转储（配合 JmapHeapDemo）

`JmapHeapDemo` 模拟内存泄漏：静态 `List<byte[]>` + `List<String>` 只增不减。

### 一键抓取

```bash
cd /path/to/java-snippets
chmod +x mat/*.sh
./mat/capture-heap.sh
```

产出：`mat/out/jmap-heap-demo.hprof`（约 18MB，随泄漏轮数增长）。

### 手动步骤

```bash
# 终端 1
mvn compile -DskipTests
java -Xmx256m -cp target/classes io.weli.concurrent.JmapHeapDemo

# 终端 2（等几秒）
PID=$(jps -l | grep JmapHeapDemo | awk '{print $1}')
jmap -dump:live,format=b,file=mat/out/jmap-heap-demo.hprof "$PID"
```

---

## 四、GUI 分析（推荐第一次练习）

### 1. 打开堆转储

```bash
open -a MemoryAnalyzer mat/out/jmap-heap-demo.hprof
```

首次打开会 **Parsing heap dump** 并生成 `.index` 文件（同目录，下次打开更快）。

### 2. 先看 Leak Suspects 报告

菜单：**Leak Suspects Report**（或欢迎页直接点）。

本 Demo 实际结论（已验证）：

> **Problem Suspect 1**：类 `io.weli.concurrent.JmapHeapDemo` 占 **85.47%** 堆（Retained **7.3 MB**）。  
> 主要消费者：`byte[]`（约 13,027 个）、`java.lang.String`（约 13,014 个）。  
> 内存累积在 `java.lang.Object[]` 实例上 —— 即 `LEAKED_BUFFERS` / `STRING_LEAK` 两个 ArrayList 的内部数组。

红色 error 图标 = MAT 认为「高度可疑」，与源码设计一致。

### 3. Histogram（类直方图）

菜单：**Java Overview → Histogram**（或工具栏图标）。

| 列 | 含义 |
|----|------|
| **Objects** | 实例个数 |
| **Shallow Heap** | 对象自身大小（不含引用对象） |
| **Retained Heap** | 回收该对象能释放的总大小 |

本 Demo：`byte[]`、`String`、`Object[]` 应排前列 —— 与 `jmap -histo` 一致，但 MAT 多 **Retained Heap** 列。

右键类 → **List objects → with incoming references** 看具体实例。

### 4. Dominator Tree（支配树）

菜单：**Dominator Tree**。

- **Retained Heap 最大** 的节点 = 谁删了能腾出最多内存
- 本 Demo 顶层应为 `JmapHeapDemo` 或 `Object[]`（ArrayList 底层数组）

展开 → 看到 `byte[]` 批量挂在 `LEAKED_BUFFERS` 路径下。

### 5. Path to GC Roots（定位泄漏根）

在 Dominator Tree 或 Histogram 里，右键可疑对象 → **Path to GC Roots → exclude weak/soft references**。

应看到类似引用链：

```
JmapHeapDemo (static)
  └─ LEAKED_BUFFERS (static field)
       └─ ArrayList
            └─ Object[] (elementData)
                 └─ byte[] ...
```

**有强引用路径到 static / ThreadLocal / 缓存** → 泄漏；若只有弱引用则可能是正常缓存。

### 6. OQL（可选）

菜单：**OQL Query**，示例：

```sql
SELECT * FROM byte[] s WHERE s.@retainedHeapSize > 1000
```

---

## 五、命令行生成报告（无 GUI）

```bash
./mat/run-parse.sh
# 或指定文件
./mat/run-parse.sh /path/to/heap.hprof
```

产出（与 `.hprof` 同目录）：

| 文件 | 内容 |
|------|------|
| `jmap-heap-demo_Leak_Suspects.zip` | 泄漏嫌疑 HTML 报告 |
| `jmap-heap-demo_System_Overview.zip` | 堆概览 |
| `jmap-heap-demo.*.index` | 索引（再次打开加速） |

查看报告：

```bash
cd mat/out
unzip -o jmap-heap-demo_Leak_Suspects.zip -d leak-report
open leak-report/index.html
```

### 解读 Leak Suspects HTML

| 区块 | 看什么 |
|------|--------|
| **Overview 饼图** | Problem Suspect vs Remainder 占比 |
| **Problem Suspect N** | 嫌疑类名、Retained Size、关键词 |
| **Details** | 引用链、Accumulated Objects 表 |
| **Top Consumers** | 最大对象 / 类 / ClassLoader |

本 Demo：**Problem Suspect 1 = `JmapHeapDemo`，85%+ 堆** → 直接打开源码查 static 集合。

---

## 六、核心概念

| 术语 | 含义 |
|------|------|
| **Shallow Size** | 对象头 + 字段，不含引用指向的对象 |
| **Retained Size** | 删除该对象后，GC 能回收的总大小（含仅被它引用的子图） |
| **Dominator** | 必须经它才能到达的对象集合的「根」 |
| **GC Root** | 线程栈、static 字段、JNI 等 GC 起点 |

**泄漏判断**：对象本应释放，但 Retained Size 很大，且 **Path to GC Roots** 指向 static 集合 / 长生命周期线程 / 缓存。

---

## 七、与 jmap 对照

| 步骤 | jmap | MAT |
|------|------|-----|
| 看谁占内存 | `jmap -histo` | Histogram / Dominator Tree |
| 导出文件 | `jmap -dump` → `.hprof` | 直接打开 `.hprof` |
| 找泄漏根 | 做不到 | Leak Suspects + Path to GC Roots |
| 适合场景 | 线上快速瞄一眼 | 线下深度分析 |

姊妹篇：`docs/jmap-troubleshooting-guide.md`

---

## 八、一条龙命令

```bash
cd /path/to/java-snippets
./mat/setup-mat-jdk.sh          # 首次
./mat/capture-heap.sh           # 生成 .hprof
./mat/run-parse.sh              # CLI 报告
open -a MemoryAnalyzer mat/out/jmap-heap-demo.hprof   # GUI
```

---

## 九、常见问题

| 问题 | 处理 |
|------|------|
| MAT 打不开 / 找不到 Java | `./mat/setup-mat-jdk.sh` 或手动改 `MemoryAnalyzer.ini` |
| Parsing 很慢 / OOM | 增大 `-Xmx`；确保磁盘空间 ≥ 堆转储 2 倍 |
| 打开 .hprof 报版本不兼容 | 用与 dump 相同或更新版本的 MAT |
| Leak Suspects 为空 | 可能没有明显泄漏；改看 Dominator Tree Top 对象 |
| 代理环境 | 下载 MAT 需要代理；分析本地 `.hprof` **不需要** |

---

## 十、相关文件

| 文件 | 说明 |
|------|------|
| `src/main/java/io/weli/concurrent/JmapHeapDemo.java` | 泄漏 Demo |
| `mat/capture-heap.sh` | 编译 + 启 Demo + jmap dump |
| `mat/setup-mat-jdk.sh` | 配置 MAT 使用 sdkman JDK |
| `mat/run-parse.sh` | CLI 生成 Leak Suspects / Overview |
| `mat/out/*.hprof` | 堆转储（gitignore，本地生成） |
