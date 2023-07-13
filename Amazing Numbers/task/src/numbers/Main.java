package numbers;

import java.math.BigInteger;
import java.util.*;

public class Main {
    static Map<String, Property> properties = new HashMap<>();
    static Map<String, String> wrongPairs = new HashMap<>();

    public static void main(String[] args) {

        properties.put("BUZZ", Check::isBuzz);
        properties.put("DUCK", Check::isDuck);
        properties.put("PALINDROMIC", Check::isPalindromic);
        properties.put("GAPFUL", Check::isGapful);
        properties.put("SPY", Check::isSpy);
        properties.put("SQUARE", Check::isSquare);
        properties.put("SUNNY", Check::isSunny);
        properties.put("JUMPING", Check::isJumping);
        properties.put("HAPPY", Check::isHappy);
        properties.put("SAD", Check::isSad);
        properties.put("EVEN", Check::isEven);
        properties.put("ODD", Check::isOdd);

        wrongPairs.put("ODD", "EVEN");
        wrongPairs.put("SQUARE", "SUNNY");
        wrongPairs.put("HAPPY", "SAD");
        wrongPairs.put("DUCK", "SPY");

        wrongPairs.put("-ODD", "-EVEN");
        wrongPairs.put("-SQUARE", "-SUNNY");
        wrongPairs.put("-HAPPY", "-SAD");
        wrongPairs.put("-DUCK", "-SPY");

        printWelcome();
        start();
    }

    private static void start() {

        Scanner scanner = new Scanner(System.in);
        String regexp = "[1-9]\\d*";

        printHelp();

        while (true) {
            System.out.print("\nEnter a request: ");
            String[] input = scanner.nextLine().trim().split("\\s+");

            if (input[0].equals("0")) break;

            if (input[0].equals("")) {
                printHelp();
                continue;
            }

            input[0] = cutZeroes(input[0]);

            if (!input[0].matches(regexp)) {
                System.out.println("The first parameter should be a natural number or zero.");
                continue;
            }

            switch (input.length) {
                case 1 -> printLongStat(input[0]);
                case 2 -> printStatForSequence(input);
                default -> searchNumbersWithProperties(input);
            }
        }
        System.out.println("Goodbye!");
    }

    private static void searchNumbersWithProperties(String[] input) {
        int repeat = Integer.parseInt(input[1]);
        if (repeat <= 0) {
            System.out.println("The second parameter should be a natural number.");
            return;
        }

        Set<Property> propertiesOn = new HashSet<>();
        Set<Property> propertiesOff = new HashSet<>();
        Set<String> wrongProps = new HashSet<>();
        for (int i = 2; i < input.length; i++) {
            input[i] = input[i].toUpperCase();

            if (input[i].charAt(0) == '-') {
                String key = input[i].substring(1);
                if (!properties.containsKey(key)) wrongProps.add(input[i]);
                else propertiesOff.add(properties.get(key));
            } else {
                if (!properties.containsKey(input[i])) wrongProps.add(input[i]);
                else propertiesOn.add(properties.get(input[i]));
            }
        }

        if (wrongProps.size() > 0) {
            if (wrongProps.size() == 1) {
                System.out.println("The property " + wrongProps + " is wrong.");
            } else {
                System.out.println("The properties " + wrongProps + " are wrong.");
            }
            System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
            return;
        }

        List<String> propNames = Arrays.stream(input).skip(2).toList();

        if (arePropertiesWrong(propNames)) return;

        searchNumbers(input[0], repeat, propertiesOn, propertiesOff);
    }

    private static boolean arePropertiesWrong(List<String> propNames) {

        for (String prop : propNames) {
            if (wrongPairs.containsKey(prop) && propNames.contains(wrongPairs.get(prop))) {
                System.out.println("The request contains mutually exclusive properties: [" + prop + ", " + wrongPairs.get(prop) + "]\n" +
                        "There are no numbers with these properties.");
                return true;
            }

            if (prop.charAt(0) != '-' && propNames.contains("-" + prop)) {
                System.out.println("The request contains mutually exclusive properties: [-" + prop + ", " + prop + "]\n" +
                        "There are no numbers with these properties.");
                return true;
            }
        }

        return false;
    }

    private static void searchNumbers(String str, int repeat, Set<Property> propertiesOn, Set<Property> propertiesOff) {
        int i = 0;
        BigInteger nBigInt = new BigInteger(str);

        while (i < repeat) {
            boolean satisfiesAll = true;
            String numStr = nBigInt.toString();

            for (Property property : propertiesOn) {
                if (!property.check(numStr)) {
                    satisfiesAll = false;
                    break;
                }
            }

            for (Property property : propertiesOff) {
                if (property.check(numStr)) {
                    satisfiesAll = false;
                    break;
                }
            }

            if (satisfiesAll) {
                printShortStat(nBigInt);
                i++;
            }

            nBigInt = nBigInt.add(BigInteger.ONE);
        }
    }

    private static void printStatForSequence(String[] input) {

        BigInteger nBigInt = new BigInteger(input[0]);

        int repeat = Integer.parseInt(input[1]);
        if (repeat <= 0) {
            System.out.println("The second parameter should be a natural number.");
            return;
        }

        for (int i = 0; i < repeat; i++) {
            printShortStat(nBigInt);
            nBigInt = nBigInt.add(BigInteger.ONE);
        }
    }

    private static void printShortStat(BigInteger nBigInt) {
        String numStr = nBigInt.toString();
        System.out.print(represent(numStr) + " is ");
        if (Check.isBuzz(numStr)) System.out.print("buzz, ");
        if (Check.isDuck(numStr)) System.out.print("duck, ");
        if (Check.isPalindromic(numStr)) System.out.print("palindromic, ");
        if (Check.isGapful(numStr)) System.out.print("gapful, ");
        if (Check.isSpy(numStr)) System.out.print("spy, ");
        if (Check.isSquare(numStr)) System.out.print("square, ");
        if (Check.isSunny(numStr)) System.out.print("sunny, ");
        if (Check.isJumping(numStr)) System.out.print("jumping, ");
        if (Check.isHappy(numStr)) System.out.print("happy, ");
        if (Check.isSad(numStr)) System.out.print("sad, ");

        if (Check.isEven(numStr)) System.out.println("even");
        else System.out.println("odd");
    }

    private static void printLongStat(String numString) {
        System.out.println("\nProperties of " + represent(numString));
        System.out.println("        buzz: " + Check.isBuzz(numString));
        System.out.println("        duck: " + Check.isDuck(numString));
        System.out.println(" palindromic: " + Check.isPalindromic(numString));
        System.out.println("      gapful: " + Check.isGapful(numString));
        System.out.println("         spy: " + Check.isSpy(numString));
        System.out.println("      square: " + Check.isSquare(numString));
        System.out.println("       sunny: " + Check.isSunny(numString));
        System.out.println("     jumping: " + Check.isJumping(numString));
        System.out.println("       happy: " + Check.isHappy(numString));
        System.out.println("         sad: " + Check.isSad(numString));
        System.out.println("        even: " + Check.isEven(numString));
        System.out.println("         odd: " + Check.isOdd(numString));
    }

    private static String cutZeroes(String str) {
        if (str == null || str.isEmpty()) return "";

        int i = 0;
        while (i < str.length()) {
            if (str.charAt(i) != '0') break;
            i++;
        }

        return str.substring(i);
    }

    private static String represent(String str) {
        if (str == null || str.isEmpty()) return "";

        int len = str.length();
        if (len <= 3) return str;

        int i = len % 3;
        if (i == 0) i = 3;

        StringBuilder response = new StringBuilder(str.substring(0, i));

        while (i < str.length()) {
            response.append(",").append(str.substring(i, i + 3));
            i += 3;
        }

        return response.toString();
    }

    private static void printWelcome() {
        System.out.println("Welcome to Amazing Numbers!");
    }

    private static void printHelp() {
        System.out.println("""

                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be processed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.""");
    }

}

