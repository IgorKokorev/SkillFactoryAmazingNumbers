package numbers;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class Check {

    static boolean isSad(String str) {
        return !isHappy(str);
    }
    static boolean isHappy(String str) {
        Long num = 0L;

        for (char ch: str.toCharArray()) {
            num += (ch - '0') * (ch - '0');
        }

        Set<Long> digits = new HashSet<>();
        while (num > 1) {
            if (digits.contains(num)) return false;
            if (num < 10) digits.add(num);

            num = happyNextIter(num);
        }

        return true;
    }

    private static long happyNextIter(long num) {
        long result = 0;

        while (num > 0) {
            result += (num % 10) * (num % 10);
            num /= 10;
        }

        return result;
    }

    static boolean isJumping(String str) {
        if (str.length() == 1) return true;
        char chPrev = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            char chNext = str.charAt(i);
            if (Math.abs(chPrev - chNext) != 1) return false;
            chPrev = chNext;
        }

        return true;
    }
    static boolean isSquare(String str) {
        BigInteger nBigInt = new BigInteger(str);

        return isSquare(nBigInt);
    }

    private static boolean isSquare(BigInteger nBigInt) {
        BigInteger sqrt = nBigInt.sqrt();

        return sqrt.multiply(sqrt).equals(nBigInt);
    }

    static boolean isSunny(String str) {
        BigInteger nBigInt = new BigInteger(str);

        return isSquare(nBigInt.add(BigInteger.ONE));
    }
    static boolean isDuck(String str) {
        return str.contains("0");
    }

    private static boolean EndsWith(String str, char end) {
        if (str == null || str.isEmpty()) return false;
        return str.charAt(str.length() - 1) == end;
    }

    private static boolean isDivisibleBy(String numString, int div) {
        BigInteger nBigInt = new BigInteger(numString);

        return nBigInt.remainder(BigInteger.valueOf(div)).equals(BigInteger.ZERO);
    }

    static boolean isPalindromic(String str) {
        char[] charArr = str.toCharArray();
        char[] newCharArr = new char[charArr.length];

        for (int i = 0; i < charArr.length; i++) {
            newCharArr[i] = charArr[charArr.length - 1 - i];
        }

        return str.equals(new String(newCharArr));
    }

    static boolean isBuzz(String numString) {
        return isDivisibleBy(numString, 7) || EndsWith(numString, '7');
    }

    static boolean isEven(String numString) {
        return isDivisibleBy(numString, 2);
    }

    static boolean isOdd(String numStr) {
        return !isEven(numStr);
    }

    static boolean isSpy(String numString) {
        long sum = 0;
        long prod = 1;

        for (char ch: numString.toCharArray()) {
            int digit = ch - '0';
            sum += digit;
            prod *= digit;
        }

        return sum == prod;
    }

    static boolean isGapful(String numString) {
        if (numString.length() < 3) return false;

        int div = Integer.parseInt("" + numString.charAt(0) + numString.charAt(numString.length() - 1));

        return isDivisibleBy(numString, div);
    }
}
