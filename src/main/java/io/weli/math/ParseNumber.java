package io.weli.math;


public class ParseNumber {


    /*
     * Complete the 'superDigit' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING n
     *  2. INTEGER k
     */

//    public static int superDigit(String n, int k) {
//        // Write your code here
//
//        System.out.println("n length: " + n.length());
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < k; i++) {
//            sb.append(n);
//        }
//
//        String out = sb.toString();
//
//        while (out.length() > 1) {
//            char[] chars = out.toCharArray();
//            System.out.println("length: " + chars.length);
//            long sum = 0;
//
//            for (char c : chars) {
//                sum += Integer.parseInt(String.valueOf(c));
//            }
//
//            System.out.println("sum: " + sum);
//
//            // System.out.println("sum: " + sum);
//
//            out = String.valueOf(sum);
//        }
//
//        return Integer.parseInt(out);
//    }

    public static long doSum(String str) {
        char[] chars = str.toCharArray();
        long sum = 0;

        for (char c : chars) {
            sum += Integer.parseInt(String.valueOf(c));
        }
        return sum;
    }

    public static int superDigit(String n, int k) {
        // Write your code here

        // prevent OutOfMemory error.
        long start = doSum(n) * k;

        String out = String.valueOf(start);

        while (out.length() > 1) {
            out = String.valueOf(doSum(out));
        }

        return Integer.parseInt(out);
    }

    public static void main(String[] args) {
        int x = superDigit(
                "7404954009694227446246375747227852213692570890717884174001587537145838723390362624487926131161112710589127423098959327020544003395792482625191721603328307774998124389641069884634086849138515079220750462317357487762780480576640689175346956135668451835480490089962406773267569650663927778867764315211280625033388271518264961090111547480467065229843613873499846390257375933040086863430523668050046930387013897062106309406874425001127890574986610018093859693455518413268914361859000614904461902442822577552997680098389183082654625098817411306985010658756762152160904278169491634807464356130877526392725432086439934006728914411061861235300979536190100734360684054557448454640750198466877185875290011114667186730452681943043971812380628117527172389889545776779555664826488520325234792648448625225364535053605515386730925070072896004645416713682004600636574389040662827182696337187610904694029221880801372864040345567230941110986028568372710970460116491983700312243090679537497139499778923997433720159174153",
                100000
        );
        System.out.println(x);
    }
}
