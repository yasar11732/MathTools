package com.github.yasar.MathTools;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yaşar Arabacı
 */
public class NarcissisticNumberFinder {

    public static List<Integer> findNarcissisticNumbers(Integer start, Integer end) {
        List<Integer> narcists = new ArrayList();
        for (int current=start; current <= end; current++) {
            String num = (new Integer(current)).toString();
            int numDigits = num.length();
            int power = num.length();
            int sum = 0;
            for (int i = 0; i < numDigits; i++) {
                int digit = num.charAt(i) - '0';
                sum += Math.pow(digit, power);
            }
            if (sum == start) {
                narcists.add(start);
            }
        }
        return narcists;
    }
}
