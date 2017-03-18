package concurrent;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class TestLinkList {

    static class List {
        private String val;
        private List next;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public List getNext() {
            return next;
        }

        public void setNext(List next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        List list = new List();
        list.setNext(new List());
        List next = list.getNext();
        list.getNext().setVal("next");
        System.out.println(next.getVal());

    }

}
