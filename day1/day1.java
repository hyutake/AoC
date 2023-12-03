import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Day 1: trebuchet
public class day1 {
    // Store numerical value of string digit
    private static Map<String, Integer> mapStringDigitToInt = Map.of("zero", 0, "one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8, "nine", 9);
    private static String[] allDigits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the inputs below: ");
        int totalSum = 0;
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("quit")) break;
            // additional step for day 1 part 2
            String parsedString = parseStringForDigitsInLetters(s);
            int sum = findSumOfFirstAndLastDigit(parsedString);
            // System.out.println("Sum: " + sum);
            totalSum += sum;
        }
        System.out.println("Total sum: " + totalSum);
        return;
    }

    /* Idea:
        - Get indexOf value for each digit string type (-1 means it doesn't exist)
        - Identify the minimum indexOf value (i.e. the earliest appearing matching digit string)
        - Replace that corresponding digit string with the digit value
        - Repeat steps 1 - 3 again until there are no longer any existing digit string
        - Logical error: oneight should ==> '18', but this does not do that
     */
    // public static String parseStringForDigitsInLetters(String s) {
    //     boolean hasDigitString = false;
    //     int minIndex = Integer.MAX_VALUE;
    //     String earliestDigitString = "";
        
    //     for(int i = 0; i < allDigits.length; i++) {
    //         String digit = allDigits[i];
    //         int index = s.indexOf(digit);
    //         if(index != -1) {
    //             hasDigitString = true;
    //             if(index < minIndex) {
    //                 minIndex = index;
    //                 earliestDigitString = digit;
    //             }
    //         }
    //     }

    //     if(!hasDigitString) return s;
    //     else return parseStringForDigitsInLetters(s.replace(earliestDigitString, digitMapper.get(earliestDigitString).toString()));
    // }

    // Note: 'oneight' should be parsed as '18' :/
    public static String parseStringForDigitsInLetters(String s) {
        Map<Integer, Integer> indexPositionToDigit = new HashMap<>();
        // find indexes of digits in strings
        for(int i = 0; i < allDigits.length; i++) {
            String digit = allDigits[i];
            int index = s.indexOf(digit);
            int fromIndex = 0;
            while(index != -1) {
                indexPositionToDigit.put(index, mapStringDigitToInt.get(digit));
                fromIndex = index + 1;
                index = s.indexOf(digit, fromIndex);
            }
        }
        // find indexes of regular digits
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                indexPositionToDigit.put(i, Integer.parseInt(Character.toString(c)));
            }
        }

        // Create and sort a List from the entries of the HashMap
        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(indexPositionToDigit.entrySet());
        entryList.sort(Map.Entry.comparingByKey());
        
        // Build the digit string - loses all the non-digit related characters
        StringBuilder digitString = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : entryList) {
            digitString.append(entry.getValue());
        }
        return digitString.toString();
    }

    public static int findSumOfFirstAndLastDigit(String s) {
        // temporarily assign base value as a 2 digit number (which is technically impossible)
        String firstDigit = "10", lastDigit = "10";
        for(char c: s.toCharArray()) {
            if(Character.isDigit(c)) {
                if(firstDigit.equals("10")) firstDigit = Character.toString(c);
                lastDigit = Character.toString(c);
            }
        }
        return Integer.parseInt(firstDigit + lastDigit);
    }
}