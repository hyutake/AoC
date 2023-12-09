package AoC.day8;

import java.util.Scanner;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class day8 {
    public static Map<String, Node> desertMap = new HashMap<>();
    public static void main(String[] args) {
        boolean debug = false;
        if(args.length > 0 && args[0].equals("debug")) debug = true;
        // runPartOne(debug);
        runPartTwo(debug);
    }

    public static void runPartOne(boolean debug) {
        Scanner sc = new Scanner(System.in);
        boolean checkedFirstLine = false;
        List<Character> instructions = new ArrayList<>();
        System.out.println("Enter the inputs below:");
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if(!checkedFirstLine) { // contains L/R instructions
                for(char c:s.toCharArray()) instructions.add(c);
                checkedFirstLine = true;
                continue;
            }
            if(s.equalsIgnoreCase("quit")) break;
            if(s.isEmpty()) continue;
            String id = s.substring(0, 3);
            desertMap.put(id, parseStringForNode(s));
        }

        if(debug) printMap(desertMap);

        int steps = 0, totalInstructions = instructions.size();
        String start = "AAA", end = "ZZZ";
        String current = start;
        while(!current.equals(end)) {
            char instruction = instructions.get(steps % totalInstructions);
            if(instruction == 'L') {
                current = desertMap.get(current).getLeft();
            } else {
                current = desertMap.get(current).getRight();
            }
            steps++;
        }

        System.out.println("Total steps taken: " + steps);
    }

    public static Node parseStringForNode(String s) {
        String[] temp = s.split("[=(,)]");
        return new Node(temp[0].trim(), temp[2].trim(), temp[3].trim());
    }

    /*
        Note: start refers to any node ending with 'A', end refers to any node ending with 'Z'

        Apparently, by finding the lowest common multiple (LCM) of the no. of steps necessary to get from start to end,
            - it will be equal to the total amount of steps needed for ALL of the starting points to reach the ending points

        This is kind of a "flawed" solution, because it assumes that each 'sequence of steps' taken to get from the start to the end is cyclically fixed
            - Meaning that say it takes 5 L/R steps to get from the start to the end: it will still be at the same ending point in 10, 15, 20... steps
            - This is not necessarily true, but it IS the case for this puzzle input
    */
    public static void runPartTwo(boolean debug) {
        Scanner sc = new Scanner(System.in);
        boolean checkedFirstLine = false;
        List<Character> instructions = new ArrayList<>();
        System.out.println("Enter the inputs below:");
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if(!checkedFirstLine) { // contains L/R instructions
                for(char c:s.toCharArray()) instructions.add(c);
                checkedFirstLine = true;
                continue;
            }
            if(s.equalsIgnoreCase("quit")) break;
            if(s.isEmpty()) continue;
            String id = s.substring(0, 3);
            desertMap.put(id, parseStringForNode(s));
        }

        if(debug) printMap(desertMap);

        int totalInstructions = instructions.size();

        List<String> current = new ArrayList<>();
        for(String key: desertMap.keySet()) {
            if(key.charAt(key.length() - 1) == 'A') current.add(key);
        }

        if(debug) printList(current);
        
        List<Integer> stepsFromStart = new ArrayList<>();

        for(int i = 0; i < current.size(); i++) {
            int step = 0;
            String cur = current.get(i);
            while(!isCurrentNodeTerminal(cur)) {
                char instruction = instructions.get(step % totalInstructions);
                if(instruction == 'L') {
                    cur = desertMap.get(cur).getLeft();
                } else {
                    cur = desertMap.get(cur).getRight();
                }
                step++;
            }
            stepsFromStart.add(step);
        }

        if(debug) printList(stepsFromStart);

        // Brute force method - too long :/
        // while(!allCurrentNodesReachedZ(current)) {
        //     if(instructionIndex == totalInstructions) {
        //         instructionIndex = 0;
        //     }
        //     char instruction = instructions.get(instructionIndex);
        //     if(instruction == 'L') {
        //         for(int i = 0; i < current.size(); i++) {
        //             current.set(i, desertMap.get(current.get(i)).getLeft());
        //         }
        //     } else {
        //         for(int i = 0; i < current.size(); i++) {
        //             current.set(i, desertMap.get(current.get(i)).getRight());
        //         }
        //     }
        //     steps++;
        //     instructionIndex++;
        // }

        System.out.println("Total steps taken: " + findLowestCommonMultiple(stepsFromStart));
    }

    public static double findLowestCommonMultiple(List<Integer> ar) {
        if (ar == null || ar.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }

        double lcm = ar.get(0);
        for (int i = 1; i < ar.size(); i++) {
            lcm = calculateLCM(lcm, ar.get(i));
        }
        return lcm;
    }

    // Method to calculate the LCM of two numbers
    public static double calculateLCM(double a, double b) {
        return (a * b) / calculateGCD(a, b);
    }

    // Method to calculate the GCD (Greatest Common Divisor) of two numbers using Euclidean algorithm
    public static double calculateGCD(double a, double b) {
        while (b != 0) {
            double temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static boolean isCurrentNodeTerminal(String node) {
        return node.charAt(node.length() - 1) == 'Z';
    }

    public static boolean allCurrentNodesReachedZ(List<String> current) {
        for(String node: current) {
            // check last char is 'Z'
            if(node.charAt(node.length() - 1) != 'Z') return false;
        }
        return true;
    }

    public static void printList(List l) {
        System.out.println("Printing list!");
        for(int i = 0; i < l.size(); i++) {
            if(i > 0) System.out.print(", ");
            System.out.print(l.get(i).toString());
        }
        System.out.println();
    }

    public static void printMap(Map<String, Node> map) {
        System.out.println("Printing map!");
        System.out.println("{");
        for(Entry<String, Node> entry: map.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue().toString());
        }
        System.out.println("}");
    }
}
