# JMeter 压测实操手册

本手册基于仓库中的 `JmeterTargetServer` + `jmeter/hello-load-test.jmx`，**已在本机（sdkman JMeter 5.6.3）实际跑通**。

---

## 一、整体流程

```
终端 1: 启动被测 HTTP 服务 (JmeterTargetServer :18080)
终端 2: jmeter -n 无 GUI 压测 → 生成 results.jtl
```

| 组件 | 路径 |
|------|------|
| 被测服务 | `src/main/java/io/weli/concurrent/JmeterTargetServer.java` |
| 测试计划 | `jmeter/hello-load-test.jmx` |
| 一键脚本 | `jmeter/run-demo.sh` |

---

## 二、安装 JMeter（sdkman + 国内镜像）

`archive.apache.org` 直连/代理常 SSL 失败，推荐 **镜像下载 + 解压到 sdkman 目录**。

### 1. 代理写法（常见坑）

```bash
# 错：locahost 拼写错误、缺少 http://
export http_proxy=locahost:7890

# 对
export http_proxy=http://localhost:7890
export https_proxy=http://localhost:7890
```

### 2. 下载并安装到 sdkman 目录（不要用 /tmp）

```bash
export http_proxy=http://localhost:7890 https_proxy=http://localhost:7890

curl -L -o ~/Downloads/apache-jmeter-5.6.3.zip \
  https://mirrors.tuna.tsinghua.edu.cn/apache/jmeter/binaries/apache-jmeter-5.6.3.zip

mkdir -p ~/.sdkman/candidates/jmeter
unzip -q ~/Downloads/apache-jmeter-5.6.3.zip -d ~/.sdkman/candidates/jmeter/
mv ~/.sdkman/candidates/jmeter/apache-jmeter-5.6.3 ~/.sdkman/candidates/jmeter/5.6.3

source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk default jmeter 5.6.3
jmeter --version
```

安装结果应为：

```
~/.sdkman/candidates/jmeter/
├── 5.6.3/          ← 真实文件
└── current -> 5.6.3
```

**不要** `sdk install jmeter 5.6.3-local /tmp/...` —— 链到 `/tmp` 会被系统清掉。

### 3. 若坚持用 sdk install

只有目录已在持久路径时才行：

```bash
sdk install jmeter 5.6.3-local ~/.sdkman/candidates/jmeter/5.6.3
sdk default jmeter 5.6.3
```

---

## 三、代理与 JMeter 运行

| 阶段 | 是否需要代理 |
|------|-------------|
| sdkman / curl 下载 JMeter | 国内通常 **需要** |
| 压测 `127.0.0.1:18080` | **不需要**，且应 unset 代理 |

压测本地服务前清掉代理，否则请求可能走代理失败：

```bash
unset http_proxy https_proxy HTTP_PROXY HTTPS_PROXY all_proxy ALL_PROXY
```

---

## 四、一键跑通（推荐）

```bash
cd /path/to/java-snippets
chmod +x jmeter/run-demo.sh
./jmeter/run-demo.sh
```

脚本会：编译 → 启动 `JmeterTargetServer` → curl 探活 → 跑 5 线程 × 10 循环 → 输出样本数。

---

## 五、手动分步（理解原理）

### 1. 编译 & 启动被测服务

```bash
mvn compile -DskipTests
java -cp target/classes io.weli.concurrent.JmeterTargetServer
```

输出：

```
JmeterTargetServer started
  PID  = ...
  URL  = http://127.0.0.1:18080/hello
```

另终端验证：

```bash
curl http://127.0.0.1:18080/hello
# {"message":"hello","thread":"..."}
```

### 2. 无 GUI 压测

```bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
unset http_proxy https_proxy HTTP_PROXY HTTPS_PROXY

mkdir -p jmeter/out
jmeter -n \
  -t jmeter/hello-load-test.jmx \
  -l jmeter/out/results.jtl \
  -j jmeter/out/jmeter-run.log
```

参数说明：

| 参数 | 含义 |
|------|------|
| `-n` | 非 GUI（CLI）模式 |
| `-t` | 测试计划 `.jmx` |
| `-l` | 结果文件 `.jtl`（CSV） |
| `-j` | JMeter 运行日志 |

### 3. 看结果

```bash
# 样本总数（减表头）
tail -n +2 jmeter/out/results.jtl | wc -l
# 期望 50 = 5 线程 × 10 循环

# 看失败行
grep ',false,' jmeter/out/results.jtl

# 看响应码分布
cut -d, -f4 jmeter/out/results.jtl | sort | uniq -c
```

`.jtl` 主要列：`timeStamp,elapsed,label,responseCode,success,bytes,threadName`

### 4. 生成 HTML 报告

```bash
JMETER="$HOME/.sdkman/candidates/jmeter/current/bin/jmeter"
rm -rf jmeter/out/report
"$JMETER" -g jmeter/out/results.jtl -o jmeter/out/report
open jmeter/out/report/index.html
```

`-g` 从已有 `.jtl` 生成报告，**不再跑压测**，可随时重复生成。

---

## 六、解读 HTML 报告（`jmeter/out/report/index.html`）

用浏览器打开 `index.html`。左侧菜单可进子页；**先看 Dashboard，再按需下钻 Charts**。

### 6.1 页面结构一览

| 区域 / 菜单 | 文件 | 看什么 |
|-------------|------|--------|
| **Dashboard** | `index.html` | 总览：成功率、延迟、吞吐、错误 |
| **Over Time** | `content/pages/OverTime.html` | 响应时间、线程数随时间变化 |
| **Throughput** | `content/pages/Throughput.html` | 吞吐（请求/秒）随时间变化 |
| **Response Times** | `content/pages/ResponseTimes.html` | 百分位延迟随时间变化 |

底层数据在 `report/statistics.json`，与页面表格一致。

### 6.2 Dashboard 顶部：Test and Report information

| 字段 | 含义 |
|------|------|
| Source file | 来源 `.jtl` 文件名 |
| Start / End Time | 压测起止时间 |
| Filter for display | 展示过滤条件（通常为空） |

确认 Source 是本次 `results.jtl`，时间范围合理。

### 6.3 APDEX（应用性能指数）

APDEX 把响应时间分成「满意 / 可容忍 / 失败」三档，输出 **0～1**，越接近 1 越好。

默认阈值（未改 `jmeter.properties` 时）：

| 档位 | 条件 |
|------|------|
| 满意 (Satisfied) | 响应时间 ≤ 500 ms |
| 可容忍 (Tolerating) | 500 ms < 响应时间 ≤ 2000 ms |
| 失败 (Frustrated) | > 2000 ms 或请求失败 |

本 Demo 本地压测，`GET /hello` 大多 1～3 ms，**APDEX 应接近 1.0**。若线上接口 APDEX 掉到 0.7 以下，说明大量用户感知到慢。

### 6.4 Requests Summary（饼图）

- **绿色 PASS**：`success=true` 的样本
- **红色 FAIL**：失败样本（超时、4xx/5xx、连接失败等）

本 Demo 期望 **100% PASS**（50/50）。出现 FAIL 时，先看下方 Errors 表，再对照 `results.jtl` 里 `success=false` 的行。

### 6.5 Statistics 表（核心）

按 **Sampler**（本例为 `GET /hello`）和 **Total** 汇总。本 Demo 实际数据示例：

| 列 | 本 Demo 约值 | 怎么读 |
|----|-------------|--------|
| **#Samples** | 50 | 总请求数，应 = 线程数 × 循环数 |
| **FAIL** | 0 | 失败数，应为 0 |
| **Error %** | 0% | 失败率，生产常盯 < 1% |
| **Average** | ~2 ms | 平均响应时间 |
| **Min / Max** | 0 / 41 ms | 最快 / 最慢；Max 偶发尖刺需结合 Over Time 看是否孤立 |
| **Median (50th)** | ~1 ms | 一半请求的延迟 ≤ 此值，比 Average 更能代表「典型体验」 |
| **90th / 95th / 99th pct** | 2 / 3 / 41 ms | 百分位延迟；**95th、99th 看长尾**，Average 容易被少数慢请求拉高 |
| **Throughput** | ~76/sec | 吞吐：每秒完成多少请求（越高越好，受线程数、RT、服务端能力限制） |
| **Received KB/sec** | ~11.5 | 下行带宽 |
| **Sent KB/sec** | ~8.8 | 上行带宽 |

**读法口诀：**

1. **Error % 先归零** — 有失败再谈性能
2. **Median + 95th pct 看体验** — Average 仅作参考
3. **Throughput 结合线程数** — 线程太少时吞吐上限低，不代表服务端极限
4. **Max 尖刺** — 本 Demo 首请求 Max=41 ms（含 TCP 建连），后续多为 1～3 ms，属正常

与 CLI 输出对照：

```
summary = 50 in 00:00:01 = 58.5/s Avg: 2 Min: 1 Max: 39 Err: 0 (0.00%)
```

HTML 报告与终端 summary 同一批数据，只是展示更细（百分位、图表）。

### 6.6 Errors / Top 5 Errors by sampler

无失败时两表为空。**有失败时**：

- **Errors**：按错误类型聚合（如 `Connection refused`、`500 Internal Server Error`）
- **Top 5 Errors by sampler**：哪个接口错最多

排查顺序：错误消息 → 对应时间段 Over Time 是否线程飙高 → 服务端日志。

### 6.7 Charts 子页

**Over Time**

- **Response Times Over Time**：各时间点平均/中位延迟；突刺 = 该秒变慢
- **Active Threads Over Time**：并发线程是否按 Ramp-up 爬升；应看到 1 秒内从 0 到 5

**Throughput**

- **Transactions/s**：每秒成功事务数；稳定平台 = 系统处理跟得上；持续下跌可能服务端瓶颈或错误增多

**Response Times**

- **Response Time Percentiles Over Time**：90/95/99 线随时间走势；**99th 长期偏高** = 长尾问题（GC、锁、慢 SQL）

本 Demo 仅跑约 1 秒，曲线点少；**压测时间拉长（如 5 分钟）时这些图更有价值**。

### 6.8 本 Demo 合格标准（自查清单）

- [ ] `#Samples` = 50
- [ ] `Error %` = 0%
- [ ] Requests Summary 全绿
- [ ] `Median` 个位数 ms（本地 HTTP）
- [ ] `GET /hello` 与 `Total` 两行数据一致（只有一个 Sampler 时）

### 6.9 与 `.jtl` 原始行对照

`results.jtl` 每行一次请求，关键列：

```
timeStamp, elapsed, label, responseCode, success, Latency, Connect, URL
```

示例：

```
1781264296321,41,GET /hello,200,true,...,Latency=33,Connect=26
1781264296364,1,GET /hello,200,true,...,Latency=1,Connect=0
```

| 字段 | 含义 |
|------|------|
| `elapsed` | 总耗时（ms），Statistics 里的 Average/Max 来源于此 |
| `Latency` | 收到首个响应字节前的等待 |
| `Connect` | TCP 连接耗时；首请求 Connect=26 ms，后续多为 0（Keep-Alive） |
| `responseCode` | HTTP 状态码，200 = 成功 |

报告里的 Max=41 ms 就对应首行 `elapsed=41`；读懂 `.jtl` 能定位「哪一次请求慢」。

---

## 七、测试计划说明（hello-load-test.jmx）

| 配置项 | 值 |
|--------|-----|
| 线程数 | 5 |
| Ramp-up | 1 秒 |
| 循环 | 10 |
| 总请求 | 50 |
| 目标 | `GET http://127.0.0.1:18080/hello` |

GUI 编辑（可选）：

```bash
unset http_proxy https_proxy
jmeter -t jmeter/hello-load-test.jmx
```

---

## 八、常见问题

| 问题 | 原因 | 处理 |
|------|------|------|
| `sdk install jmeter` SSL 错误 | archive.apache.org 不稳定 | 用清华镜像 + 本地目录安装 |
| 链到 `/tmp` 后 jmeter 消失 | sdkman 软链临时目录 | 安装到 `~/.sdkman/candidates/jmeter/5.6.3` |
| 压测全失败 / Connection refused | 服务未起或端口不对 | 先 `curl http://127.0.0.1:18080/hello` |
| 压测走代理连不上 localhost | 环境变量 `http_proxy` | 压测前 `unset` 代理 |
| `Non HTTP response code: java.net.BindException` | 端口占用 | 改 `JmeterTargetServer` 端口或杀占用进程 |

---

## 九、清理

```bash
jps -l | grep JmeterTargetServer | awk '{print $1}' | xargs kill 2>/dev/null
rm -rf jmeter/out jmeter.log
```

---

## 十、相关文件

- 被测服务：`src/main/java/io/weli/concurrent/JmeterTargetServer.java`
- 测试计划：`jmeter/hello-load-test.jmx`
- 运行脚本：`jmeter/run-demo.sh`
- 姊妹篇：`docs/jstack-troubleshooting-guide.md`、`docs/jmap-troubleshooting-guide.md`
