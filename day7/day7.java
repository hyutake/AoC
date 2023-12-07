package AoC.day7;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class day6 {
    public static void main(String[] args) {
        // runPartOne();
        runPartTwo();
    }

    public static void runPartOne() {
        Scanner sc = new Scanner(System.in);
        int totalWinnings = 0;
        // NOTE: update string to custom data type!
        List<List<String>> ar = new ArrayList<List<String>>();
        for(int i = 0; i < 7; i++) {
          ar.add(new ArrayList<String>());
        }
        
        System.out.println("Enter the inputs below: ");
        while(sc.hasNextLine()) {
          String s = sc.nextLine();
          if(s.equalsIgnoreCase("quit")) break;
          String[] handAndBid = s.split(" ");
          int handType = identifyHandType(handAndBid[0]);
          ar.get(handType).add(handAndBid[0], Integer.parseInt(handAndBid[1]);
        }
    }

    public static void runPartTwo() {
        Scanner sc = new Scanner(System.in);
        int totalWinnings = 0;
        System.out.println("Enter the inputs below: ");
        while(sc.hasNextLine()) {
          String s = sc.nextLine();
        }
    }

    // to (hopefully) help with custom sorting
    public static int mapCardToValue(char card) {
      if(Character.isDigit(card)) {
        return Integer.parseInt(card);
      } else {
        switch(card) {
          case 'T':
            return 10;
          case 'J':
            return 11;
          case 'Q':
            return 12;
          case 'K':
            return 13;
          case 'A':
            return 14;
          default:
            return 0;
        }
      }
    }

    /* Possible hands
      - 0: High card (1 1 1 1 1)
      - 1: 1 pair (1 1 1 2)
      - 2: 2 pair (1 2 2)
      - 3: 3 of a kind (1 1 3)
      - 4: Full house (2 3)
      - 5: 4 of a kind (1 4)
      - 6: 5 of a kind (5)
    */
    public static int identifyHandType(String hand) {
      Map<Character, Integer> record = new HashMap<>();
      for(char c: hand.toCharArray()) {
        if(record.containsKey(c)) {
          record.put(c, record.get(c) + 1);
        } else record.put(c, 1);
      }
      // count the number of unique cards that only appeared once (i.e. the recorded value is 1)
      int numOfOnes = 0, maxRepeats = 0;
      Collection<Integer> values = record.values();
      for(int value: values) {
        if(value === 1) numOfOnes++;
        if(value > maxRepeats) maxRepeats = value;
      }

      switch(numOfOnes) {
        case 0:
          if(maxRepeats === 5) return 6;  // 5 of a kind
          else return 4; // full house
        case 1:
          if(maxRepeats === 4) return 5;  // 4 of a kind
          return 2;  // 2 pair
        case 2:
          return 3; // 3 of a kind
        case 3:
          return 1;  // 1 pair
        default:
          return 0;
      }
    }
}
