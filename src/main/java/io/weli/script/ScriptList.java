package io.weli.script;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import javax.script.ScriptEngine;
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
    public static void main(String[] args) throws Exception {
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

//            System.out.println("OUT: " + f.getScriptEngine().eval("from java.util import Date\n" +
//                    "d = Date()\n" +
//                    "print d\n"));
        }


        PythonInterpreter interp = new PythonInterpreter();
        System.out.println("Hello, world from Java");
//        interp.execfile("hello.py");
        interp.set("a", new PyInteger(42));
        interp.exec("print a");
        interp.exec("x = 2+2");
        PyObject x = interp.get("x");
        System.out.println("x: " + x);
        System.out.println("Goodbye ");

    }
}
