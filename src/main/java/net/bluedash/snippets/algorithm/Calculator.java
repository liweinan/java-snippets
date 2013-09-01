package net.bluedash.snippets.algorithm;

import net.bluedash.snippets.security.symmetric.Utils;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Calculator {
    public static void multiple(byte[] n1, byte[] n2, byte[] result) {
        int position = result.length - 1;

        // clear result
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }

        for (int m = n1.length - 1; m >= 0; m--) {
            int offset = n1.length - 1 - m;
            for (int n = n2.length - 1; n >= 0; n--, offset++) {
                int production = n1[m] * n2[n];
                // compute partial total by carrying previous digit's position
                result[position - offset] += production % 10;
                result[position - offset - 1] += result[position - offset] / 10 + production / 10;
                result[position - offset] %= 10;
            }
        }
    }

    public static void main(String[] args) {
        byte[] n1 = {1, 2, 3};
        byte[] n2 = {3, 2, 1};
        byte[] n3 = {1, 1, 1, 1, 1, 1, 1};

        multiple(n1, n2, n3);
        System.out.println(Utils.toHex(n3));
    }

}
