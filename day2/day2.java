import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class day2 {
    private static int MAX_RED = 12;
    private static int MAX_GREEN = 13;
    private static int MAX_BLUE = 14;
    public static void main(String[] args) {
        // runPartOne();
        runPartTwo();
    }

    // check if games were valid given the MAX values
    public static void runPartOne() {
        Scanner sc = new Scanner(System.in);
        int totalSum = 0;
        int gameId = 1;
        System.out.println("Enter the inputs below: ");
        while(sc.hasNextLine()) {
            boolean isValid = false;
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("quit")) break;
            isValid = parseStringForValidResults(s);
            if(isValid) {
                System.out.println("Game " + gameId + " is valid!");
                totalSum += gameId;
            }
            gameId++;
        }
        System.out.println("Total sum: " + totalSum);
        return;
    }

    // find the min. no. of cubes needed for each game, then get the sum of them multiplied with each other
    public static void runPartTwo() {
        Scanner sc = new Scanner(System.in);
        int totalSum = 0;
        System.out.println("Enter the inputs below: ");
        while(sc.hasNextLine()) {
            boolean isValid = false;
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("quit")) break;
            totalSum += parseStringForPowerOfMinCubes(s);
        }
        System.out.println("Total sum: " + totalSum);
        return;
    }

    // find the min. no. of cubes needed for the given game result + return the result of all of them multiplied
    public static int parseStringForPowerOfMinCubes(String s) {
        int minimalBlue = Integer.MIN_VALUE, minimalRed = Integer.MIN_VALUE, minimalGreen = Integer.MIN_VALUE;

        String[] gameResults = s.split(":")[1].trim().split(";");
        for(String gameResult: gameResults) {
            String[] game = gameResult.trim().split(",");
            for(String result: game) {
                String[] valueAndColor = result.trim().split(" ");
                String color = valueAndColor[1];
                int value = Integer.parseInt(valueAndColor[0]);
                System.out.println("Checking " + result.trim() + "!");
                if(color.equals("blue")) {
                    if(value > minimalBlue) minimalBlue = value;
                } else if(color.equals("red")) {
                    if(value > minimalRed) minimalRed = value;
                } else if(color.equals("green")) {
                    if(value > minimalGreen) minimalGreen = value;
                } else {
                    System.out.println("Invalid color type: " + color + "!");
                }
            }
        }
        System.out.println("minimalBlue: " + minimalBlue);
        System.out.println("minimalRed: " + minimalRed);
        System.out.println("minimalGreen: " + minimalGreen);
        return minimalBlue * minimalRed * minimalGreen;
    }

    // part 1
    public static boolean parseStringForValidResults(String s) {
        String[] gameResults = s.split(":")[1].trim().split(";");
        for(String gameResult: gameResults) {
            String[] game = gameResult.trim().split(",");
            for(String result: game) {
                String[] valueAndColor = result.trim().split(" ");
                String color = valueAndColor[1];
                int value = Integer.parseInt(valueAndColor[0]);
                System.out.println("Checking " + result.trim() + "!");
                if(color.equals("blue")) {
                    if(value > MAX_BLUE) return false;
                } else if(color.equals("red")) {
                    if(value > MAX_RED) return false;
                } else if(color.equals("green")) {
                    if(value > MAX_GREEN) return false;
                } else {
                    System.out.println("Invalid color type: " + color + "!");
                }
            }
        }
        return true;
    }
}
