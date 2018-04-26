package lang;

public class Finally {

    public String testFinally() throws Exception {
        try {
            return sayHello();
        } finally {
            Thread.sleep(1000);
            System.out.println("FINALLY");
        }
    }

    private String sayHello() {
        return "Hello, world!";
    }

    public static void main(String[] args) throws Exception {
        Finally fin = new Finally();
        System.out.println(fin.testFinally());
    }
}
