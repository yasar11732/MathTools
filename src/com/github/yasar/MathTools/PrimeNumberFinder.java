package com.github.yasar.MathTools;

/**
 *
 * @author Yaşar Arabacı
 */
public class PrimeNumberFinder {

    /**
     * Return an array of prime numbers up to upperLimit
     * using sieve of Erastosthenes.
     * @param upperLimit
     * @return array of prime numbers up to upperLimit
     */
    public static int[] sieve(int upperLimit) {
        // for corner cases
        if (upperLimit < 2) {
            return new int[0];
        }
        
        if (upperLimit == 2) {
            int[] result = {2};
            return result;
        }
        //--
        
        int arrLength = ((upperLimit - 3) / 2) + 1;
        
        // This arrray will be used to perform the sieve
        int[] temp = new int[arrLength];
        
        // Counter for number of primes encountered
        int numPrimes = 1; // because of 2
        
        // populate temp array with with 3,5,7,...,upperLimit
        for(int i=3; i <= upperLimit; i=i+2) {
            temp[(i-3) / 2] = i;
        }
        
        // Perform sieve
        for(int i=0; i < arrLength; i++ ) {
            int num = temp[i];
            if (num == 0) continue;
            numPrimes++;
            for (int k = i+num; k < arrLength; k=k+num) {
                temp[k] = 0;
            }
        }
        
        // Create result array
        int[] result = new int[numPrimes];
        result[0] = 2;
        int currentIndex = 1;
        
        for(int i: temp) {
            if (i!=0) result[currentIndex++] = i;
        }
        
        return result;
    }
}
