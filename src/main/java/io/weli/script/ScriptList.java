package io.weli.script;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;

/*
[INFO]
[INFO] -----------------------< io.weli:java-snippets >------------------------
[INFO] Building java-snippets 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- exec-maven-plugin:3.0.0:java (default-cli) @ java-snippets ---
Engine Name:jython
Engine Version:2.7.3
Language Name:python
Language Version:2.7
Engine Short Names:[python, jython]
Mime Types:[text/python, application/python, text/x-python, application/x-python]
 */
public class ScriptList {
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();

        // Get the list of all available engines
        List<ScriptEngineFactory> list = manager.getEngineFactories();

        // Print the details of each engine
        for (ScriptEngineFactory f : list) {
            System.out.println("Engine Name:" + f.getEngineName());
            System.out.println("Engine Version:" + f.getEngineVersion());
            System.out.println("Language Name:" + f.getLanguageName());
            System.out.println("Language Version:" + f.getLanguageVersion());
            System.out.println("Engine Short Names:" + f.getNames());
            System.out.println("Mime Types:" + f.getMimeTypes());
            System.out.println("===");
        }
    }
}
