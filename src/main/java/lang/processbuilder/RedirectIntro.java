package lang.processbuilder;

/**
 * Created by weli on 4/20/16.
 */
public class RedirectIntro {
    public static void main(String[] args) throws Exception {
        ProcessBuilder builder = new ProcessBuilder();
        System.out.println(builder.redirectInput());
        System.out.println(builder.redirectOutput());
        System.out.println(builder.redirectError());
        System.out.println(builder.redirectErrorStream());
    }
}
