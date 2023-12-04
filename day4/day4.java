import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class day4 {
    public static void main(String[] args) {
        // runPartOne();
        runPartTwo();
    }

    public static void runPartOne() {
        double totalPoints = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the cards below: ");
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("quit")) break;
            totalPoints += parseStringForPointsWon(s);
        }
        System.out.println("Total points: " + totalPoints);
        return;
    }

    public static void runPartTwo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the cards below: ");
        // figure out the total no. of cards given
        List<String> cards = new ArrayList<>();
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("quit")) break;
            cards.add(s);
        }

        int curCard = 1;
        // initialize card count array list with total no. of cards found earlier
        List<Integer> cardCount = new ArrayList<>(Collections.nCopies(cards.size(), 1));
        for(String s: cards) {
            int cardInstances = cardCount.get(curCard - 1);
            int matches = parseStringForMatches(s);
            for(int i = 0; i < matches; i++) {
                int targetIndex = curCard + i;
                if(targetIndex >= cards.size()) break;
                cardCount.set(targetIndex, cardCount.get(targetIndex) + cardInstances);
            }
            curCard++;
        }
        // printArrayList(cardCount);
        int totalCards = cardCount.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total cards: " + totalCards);
        return;
    }

    public static int parseStringForMatches(String s) {
        String winningAndSelfNums = s.split(":")[1].trim();
        String[] winningNumAndSelfNum = winningAndSelfNums.split("\\|");
        String[] winningNumbers = winningNumAndSelfNum[0].trim().split(" ");
        String[] selfNumbers = winningNumAndSelfNum[1].trim().split(" ");

        int matches = 0;
        for(String num: selfNumbers) {
            for(String winningNum: winningNumbers) {
                if(winningNum.trim().equals(num.trim()) && winningNum.trim().length() > 0) {
                    System.out.println("Matching " + winningNum + " detected in number list!");
                    matches++;
                    break;
                }
            }
        }
        return matches;
    }

    public static double parseStringForPointsWon(String s) {
        String winningAndSelfNums = s.split(":")[1].trim();
        String[] winningNumAndSelfNum = winningAndSelfNums.split("\\|");
        String[] winningNumbers = winningNumAndSelfNum[0].trim().split(" ");
        String[] selfNumbers = winningNumAndSelfNum[1].trim().split(" ");

        int matches = 0;
        for(String num: selfNumbers) {
            for(String winningNum: winningNumbers) {
                if(winningNum.trim().equals(num.trim()) && winningNum.trim().length() > 0) {
                    System.out.println("Matching " + winningNum + " detected in number list!");
                    matches++;
                    break;
                }
            }
        }
        if(matches == 0) return 0;
        else return Math.pow(2, matches - 1);
    }

    public static void printArrayList(List ar) {
        System.out.println("Card count:");
        for(int i = 0; i < ar.size(); i++) {
            if(i > 0) System.out.print(", ");
            System.out.printf("%d: %d", i + 1, ar.get(i));
        }
        System.out.println();
    }

    public static void printStringArray(String[] ar) {
        for(String s: ar) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
