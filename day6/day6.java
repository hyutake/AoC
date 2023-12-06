package AoC.day6;

import java.util.Scanner;

public class day6 {
    public static void main(String[] args) {
        // runPartOne();
        runPartTwo();
    }

    public static void runPartOne() {
        Scanner sc = new Scanner(System.in);
        int totalWays = 0;
        System.out.println("Enter the inputs below (time string followed by distance string): ");

        String timeStr = sc.nextLine();
        String[] timings = timeStr.split("\\s+");

        String distStr = sc.nextLine();
        String[] dist = distStr.split("\\s+");

        for(int i = 1; i < timings.length; i++) {
            int winningWays = findNumOfWinningWays(Integer.parseInt(timings[i]), Integer.parseInt(dist[i]));
            if(totalWays == 0) totalWays += winningWays;
            else totalWays *= winningWays;
        }
        System.out.println("Total ways: " + totalWays);
    }

    public static void runPartTwo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the inputs below (time string followed by distance string): ");

        String timeStr = sc.nextLine();
        StringBuilder time = new StringBuilder();
        for(char c: timeStr.toCharArray()) {
            if(Character.isDigit(c)) {
                time.append(c);
            }
        }
        double timeLimit = Double.parseDouble(time.toString());

        String distStr = sc.nextLine();
        StringBuilder dist = new StringBuilder();
        for(char c: distStr.toCharArray()) {
            if(Character.isDigit(c)) {
                dist.append(c);
            }
        }
        double distToBeat = Double.parseDouble(dist.toString());

        double totalWays = findNumOfWinningWays(timeLimit, distToBeat);
        System.out.println("Total ways: " + totalWays);
    }

    public static double findNumOfWinningWays(double timeLimit, double distToBeat) {
        double count = 0;
        // checking for 0 and timeLimit is redundant because both are guaranteed to yield 0 distance
        for(double i = 1; i < timeLimit; i++) {
            if(i * (timeLimit - i) > distToBeat) {
                count++;
            }
        }
        return count;
    }

    public static int findNumOfWinningWays(int timeLimit, int distToBeat) {
        int count = 0;
        // checking for 0 and timeLimit is redundant because both are guaranteed to yield 0 distance
        for(int i = 1; i < timeLimit; i++) {
            if(i * (timeLimit - i) > distToBeat) {
                count++;
            }
        }
        return count;
    }
}
