package AoC;

import java.util.List;

public class Helper {
    public static void printArray(Integer[] ar) {
        System.out.println("Printing array!");
        for(int i = 0; i < ar.length; i++) {
            if(i > 0) System.out.print(", ");
            System.out.print(ar[i].toString());
        }
        System.out.println();
    }

    public static void printArray(String[] ar) {
        System.out.println("Printing array!");
        for(int i = 0; i < ar.length; i++) {
            if(i > 0) System.out.print(", ");
            System.out.print(ar[i].toString());
        }
        System.out.println();
    }

    public static void printList(List l) {
        System.out.println("Printing list!");
        for(int i = 0; i < l.size(); i++) {
            if(i > 0) System.out.print(", ");
            System.out.print(l.get(i).toString());
        }
        System.out.println();
    }
}
