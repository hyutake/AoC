package AoC.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import AoC.Helper;

public class day9 {
    public static void main(String[] args) {
        boolean debug = false;
        if(args.length > 0 && args[0].equals("debug")) debug = true;
        // runPartOne(debug);
        runPartTwo(debug);
    }

    public static void runPartOne(boolean debug) {
        double total = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your input below:");
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("quit")) break;
            List<Double> input = parseString(s);
            if(debug) {
                System.out.println("input: ");
                Helper.printList(input);
            } 
            List<Double> output = extrapolateFuture(input, debug);

            if(debug) {
                System.out.println("output: ");
                Helper.printList(output);
            }
            total += output.get(output.size() - 1);
        }

        System.out.println("Sum of extrapolated values: " + total);
    }

    public static void runPartTwo(boolean debug) {
        double total = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your input below:");
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("quit")) break;
            List<Double> input = parseString(s);
            if(debug) {
                System.out.println("input: ");
                Helper.printList(input);
            } 
            List<Double> output = extrapolateHistory(input, debug);

            if(debug) {
                System.out.println("output: ");
                Helper.printList(output);
            }
            total += output.get(0);
        }

        System.out.println("Sum of extrapolated values: " + total);
    }

    // takes in 1 line of the input, returns the extrapolated result
    public static List<Double> parseString(String s) {
        List<Double> values = new ArrayList<>();
        String[] val = s.split("\\s+");
        for(String v: val) {
            values.add(Double.parseDouble(v));
        }
        return values;
    }

    // returns the extrapolated ver of the received array
    public static List<Double> extrapolateFuture(List<Double> ar, boolean debug) {
        List<Double> result = new ArrayList<>();
        double difference = 0;
        boolean arHasSameValues = true;

        // populate result (which is the 'lower' level of values in the visualisation)
        for(int i = 1; i < ar.size(); i++) {
            if(ar.get(i) != ar.get(i - 1)) {
                arHasSameValues = false;
            }
            difference = ar.get(i) - ar.get(i - 1);
            result.add(difference);
        }

        if(arHasSameValues) {
            result.add(difference);
            if(debug) Helper.printList(result);
            return result;
        } else {
            List<Double> nextAr = extrapolateFuture(result, debug);
            ar.add(ar.get(ar.size() - 1) + nextAr.get(nextAr.size() - 1));
            if(debug) Helper.printList(ar);
            return ar;
        }
    }

    // Need update - now need to find previous val
    public static List<Double> extrapolateHistory(List<Double> ar, boolean debug) {
        List<Double> result = new ArrayList<>();
        double difference = 0;
        boolean arHasSameValues = true;

        // populate result (which is the 'lower' level of values in the visualisation)
        for(int i = 1; i < ar.size(); i++) {
            if(ar.get(i) != ar.get(i - 1)) {
                arHasSameValues = false;
            }
            difference = ar.get(i) - ar.get(i - 1);
            result.add(difference);
        }

        if(arHasSameValues) {
            result.add(0, difference);
            if(debug) Helper.printList(result);
            return result;
        } else {
            List<Double> nextAr = extrapolateHistory(result, debug);
            ar.add(0, ar.get(0) - nextAr.get(0));
            if(debug) Helper.printList(ar);
            return ar;
        }
    }

    public static void runPartTwo() {

    }

}
