package AoC.day7;

import java.util.Scanner;
import java.util.Map.Entry;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class day7 {
    public static Map<Integer,String> mapStrToScenario = Map.of(0, "High card", 1, "1 pair", 2, "2 pair", 3, "3 of a kind", 4, "Full house", 5, "4 of a kind", 6, "5 of a kind");

    public static void main(String[] args) {
        // runPartOne(false);
        runPartTwo(true);
    }

    static class CardAndBid implements Comparable<CardAndBid> {
      public String card;
      public int bid;
      private boolean jackToJoker = false;

      public CardAndBid(String card, int bid) {
        this.card = card;
        this.bid = bid;
      }

      public CardAndBid(String card, int bid, boolean jackToJoker) {
        this.card = card;
        this.bid = bid;
        this.jackToJoker = jackToJoker;
      }
      
      @Override
      public int compareTo(CardAndBid other) {
        for(int i = 0; i < other.card.length(); i++) {
          int aValue = mapCardToValue(this.card.charAt(i));
          int bValue = mapCardToValue(other.card.charAt(i));
          if(aValue == bValue) continue;
          return aValue - bValue;
        }
        return 0;
      }

      @Override
      public String toString() {
        return card + " " + bid;
      }

      // to (hopefully) help with custom sorting
      public int mapCardToValue(char card) {
        if(Character.isDigit(card)) {
          return Integer.parseInt(Character.toString(card));
        } else {
          switch(card) {
            case 'T':
              return 10;
            case 'J':
              if(this.jackToJoker) return 1;
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
    }

    
    public static void runPartOne(boolean debug) {
        Scanner sc = new Scanner(System.in);
        int rank = 1;
        int totalWinnings = 0;

        List<List<CardAndBid>> ar = new ArrayList<List<CardAndBid>>();
        for(int i = 0; i < 7; i++) {
          ar.add(new ArrayList<CardAndBid>());
        }
        
        System.out.println("Enter the inputs below: ");
        while(sc.hasNextLine()) {
          String s = sc.nextLine();
          if(s.equalsIgnoreCase("quit")) break;
          String[] handAndBid = s.split(" ");
          // get the "strength" of the hand
          int handType = identifyHandType(handAndBid[0], debug);
          if(debug) System.out.println("Hand strength: " + handType);
          ar.get(handType).add(new CardAndBid(handAndBid[0], Integer.parseInt(handAndBid[1])));
        }

        // custom sort each array in ar
        if(debug) System.out.println("Sorting array!");
        for(List<CardAndBid> array : ar) {
          Collections.sort(array);
        }

        // update totalWinnings
        if(debug) System.out.println("Finding winnings!");
        for(List<CardAndBid> array : ar) {
          if(array.isEmpty()) continue;
          for(CardAndBid c: array) {
            totalWinnings += rank * c.bid;
            rank++;
          }
        }

        System.out.println("Total winnings: " + totalWinnings);
    }

    public static void runPartTwo(boolean debug) {
        Scanner sc = new Scanner(System.in);
        int rank = 1;
        double totalWinnings = 0;

        List<List<CardAndBid>> ar = new ArrayList<List<CardAndBid>>();
        for(int i = 0; i < 7; i++) {
          ar.add(new ArrayList<CardAndBid>());
        }
        
        System.out.println("Enter the inputs below: ");
        while(sc.hasNextLine()) {
          String s = sc.nextLine();
          if(s.equalsIgnoreCase("quit")) break;
          String[] handAndBid = s.split(" ");
          // get the "strength" of the hand
          int handType = identifyHandTypeWithJoker(handAndBid[0], debug);
          if(debug) System.out.println(mapStrToScenario.get(handType));
          ar.get(handType).add(new CardAndBid(handAndBid[0], Integer.parseInt(handAndBid[1]), true));
        }

        // custom sort each array in ar
        if(debug) {
          System.out.println("Before sorting array: ");
          printListArray(ar);
        }
        for(List<CardAndBid> array : ar) {
          Collections.sort(array);
        }
        if(debug) {
          System.out.println("After sorting array: ");
          printListArray(ar);
        }

        // update totalWinnings
        if(debug) System.out.println("Finding winnings!");
        for(List<CardAndBid> array : ar) {
          if(array.isEmpty()) continue;
          for(CardAndBid c: array) {
            totalWinnings += rank * c.bid;
            rank++;
          }
        }

        System.out.println("Total winnings: " + totalWinnings);
    }

    public static int identifyHandTypeWithJoker(String hand, boolean debug) {
      int jokerCount = 0;
      if(debug) System.out.println("Checking hand: " + hand);
      Map<Character, Integer> record = new HashMap<>();
      // should not add 'J's to the record, since these will "become" other cards
      for(char c: hand.toCharArray()) {
        if(c == 'J') {
          jokerCount++;
          continue;
        }
        if(record.containsKey(c)) {
          record.put(c, record.get(c) + 1);
        } else record.put(c, 1);
      }
      if(debug) {
        printMap(record);
        System.out.println("J: " + jokerCount);
      }

      // count the number of unique cards that only appeared once (i.e. the recorded value is 1) + identify the most no. of repeated cards
      int numOfOnes = 0, maxRepeats = 0;
      Collection<Integer> values = record.values();
      for(int value: values) {
        if(value == 1) numOfOnes++;
        if(value > maxRepeats) maxRepeats = value;
      }

      // assign joker(s) to 'optimal' card choice (i.e. the card with the most repeats)
      if(maxRepeats == 1) {
        numOfOnes--;
      }
      maxRepeats += jokerCount;
      
      switch(numOfOnes) {
        case 0:
          if(maxRepeats == 5) return 6;  // 5 of a kind
          else return 4; // full house
        case 1:
          if(maxRepeats == 4) return 5;  // 4 of a kind
          return 2;  // 2 pair
        case 2:
          return 3; // 3 of a kind
        case 3:
          return 1;  // 1 pair
        default:
          return 0;
      }
    }

    public static int identifyHandType(String hand, boolean debug) {
      Map<Character, Integer> record = new HashMap<>();
      for(char c: hand.toCharArray()) {
        if(record.containsKey(c)) {
          record.put(c, record.get(c) + 1);
        } else record.put(c, 1);
      }
      if(debug) printMap(record);

      // count the number of unique cards that only appeared once (i.e. the recorded value is 1)
      int numOfOnes = 0, maxRepeats = 0;
      Collection<Integer> values = record.values();
      for(int value: values) {
        if(value == 1) numOfOnes++;
        if(value > maxRepeats) maxRepeats = value;
      }

      switch(numOfOnes) {
        case 0:
          if(maxRepeats == 5) return 6;  // 5 of a kind
          else return 4; // full house
        case 1:
          if(maxRepeats == 4) return 5;  // 4 of a kind
          return 2;  // 2 pair
        case 2:
          return 3; // 3 of a kind
        case 3:
          return 1;  // 1 pair
        default:
          return 0;
      }
    }

    public static void printMap(Map<Character, Integer> m) {
      System.out.println("Printing map!");
      System.out.println("{");
      for(Entry<Character, Integer> entry: m.entrySet()) {
        System.out.println("  " + entry.getKey() + ": " + entry.getValue());
      }
      System.out.println("}");
    }

    public static void printListArray(List ar) {
      System.out.println("Printing list array!");
      for(int i = 0; i < ar.size(); i++) {
        System.out.println(ar.get(i).toString());
      }
      System.out.println();
    }
}
