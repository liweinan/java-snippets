package net.bluedash.snippets.classloader.demos;

/*
 * mvn install
 * cd target/classes
 * java net/bluedash/snippets/classloader/demos/UseClassLoader
 */
public class UseClassLoader {

    public void sayHello() {
        System.out.println("Hello, world!");
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        /*
         * 系统默认的ClassLoader会从标准位置查找待加载的类
         * 比如这里的net.bluedash.snippets.classloader.demos.UseClassLoader
         * 它会从当然目录开始，查找net/bluedash/snippets/classloader/demos/UseClassLoader.class这个文件
         */
        Class cl = ClassLoader.getSystemClassLoader().loadClass("net.bluedash.snippets.classl＝oader.demos.UseClassLoader");
        UseClassLoader obj = (UseClassLoader) cl.newInstance();
        obj.sayHello();

    }

}
