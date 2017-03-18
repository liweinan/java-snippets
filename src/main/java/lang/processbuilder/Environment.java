package lang.processbuilder;

import java.util.Map;

/**
 * Created by weli on 4/20/16.
 */
public class Environment {

    public static void main(String[] args) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();

        Map<String, String> envs = processBuilder.environment();

        for (Map.Entry<String, String> env : envs.entrySet()) {
            System.out.println(env);
        }
    }
}
