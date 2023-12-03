import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day3 {
    public static void main(String[] args) {
        // runPartOne();
        runPartTwo();
    }

    public static void runPartTwo() {
        Scanner sc = new Scanner(System.in);
        List<char[]> engineSchematic = new ArrayList<char[]>();
        System.out.println("Enter the schematic below: ");
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("quit")) break;
            engineSchematic.add(s.toCharArray());
        }
        int totalSum = getSumOfGearRatios(engineSchematic);
        System.out.println("Total sum: " + totalSum);
        return;
    }

    public static void runPartOne() {
        Scanner sc = new Scanner(System.in);
        List<char[]> engineSchematic = new ArrayList<char[]>();
        System.out.println("Enter the schematic below: ");
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("quit")) break;
            engineSchematic.add(s.toCharArray());
        }

        int totalSum = getSumOfPartNumbers(engineSchematic);
        System.out.println("Total sum: " + totalSum);
        return;
    }

    static class Coordinate {
        private int rowIndex;
        private int colIndex;
        public Coordinate(int rowIndex, int colIndex) {
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
        }
        public int getRowIndex() {
            return rowIndex;
        }
        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
        }
        public int getColIndex() {
            return colIndex;
        }
        public void setColIndex(int colIndex) {
            this.colIndex = colIndex;
        }
    }

    public static int getSumOfGearRatios(List<char[]> engineSchematic) {
        int total = 0;
        for(int i = 0; i < engineSchematic.size(); i++) {
            char[] row = engineSchematic.get(i);
            for(int j = 0; j < row.length; j++) {
                // find *
                char c = row[j];
                if(c == '*') {  // gear found
                    // get positions of all adjacent numbers
                    List<Coordinate> coords = checkForAdjParts(engineSchematic, i, j);
                    // has exactly 2 adjacent part numbers
                    if(coords.size() == 2) {
                        int firstPart = identifyPartNumber(engineSchematic, coords.get(0).getRowIndex(), coords.get(0).getColIndex());
                        int secondPart = identifyPartNumber(engineSchematic, coords.get(1).getRowIndex(), coords.get(1).getColIndex());
                        total += firstPart * secondPart;
                    }
                }
            }
        }
        return total;
    }

    public static List<Coordinate> checkForAdjParts(List<char[]> engineSchematic, int currentRowIndex, int currentColIndex) {
        List<Coordinate> coordinates = new ArrayList<>();
        // to prevent having the same overall number be counted several times
        boolean prevIsDigit = false;
        if(currentRowIndex == 0) {  // first row
            if(currentColIndex == 0) {    // starting on first col
                // check 1 char to the right on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex + 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex + 1));
                // check the row below
                for(int i = 0; i <= currentColIndex + 1; i++) {
                    if(!prevIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex + 1, i));
                        prevIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) prevIsDigit = false;
                }
            } else if(currentColIndex == engineSchematic.get(currentRowIndex).length - 1) { // ending on last col
                // check 1 char to the left on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex - 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex - 1));
                // check the row below
                for(int i = currentColIndex - 1; i <= currentColIndex; i++) {
                    if(!prevIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex + 1, i));
                        prevIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) prevIsDigit = false;
                }
            } else {    // in the middle cols
                // check 1 char to the left on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex - 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex - 1));
                // check 1 char to the right on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex + 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex + 1));
                // check the row below
                for(int i = currentColIndex - 1; i <= currentColIndex + 1; i++) {
                    if(!prevIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex + 1, i));
                        prevIsDigit = true;
                    } else if (!Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) prevIsDigit = false;
                }
            }
        } else if(currentRowIndex == engineSchematic.size() - 1) {  // last row
            if(currentColIndex == 0) {    // starting on first col
                // check 1 char to the right on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex + 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex + 1));
                // check the row above
                for(int i = 0; i <= currentColIndex + 1; i++) {
                    if(!prevIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex - 1, i));
                        prevIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) prevIsDigit = false;
                }
            } else if(currentColIndex == engineSchematic.get(currentRowIndex).length - 1) { // ending on last col
                // check 1 char to the left on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex - 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex - 1));
                // check the row above
                for(int i = currentColIndex - 1; i <= currentColIndex; i++) {
                    if(!prevIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex - 1, i));
                        prevIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) prevIsDigit = false;
                }
            } else {    // in the middle cols
                // check 1 char to the left on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex - 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex - 1));
                // check 1 char to the right on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex + 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex + 1));
                // check the row above
                for(int i = currentColIndex - 1; i <= currentColIndex + 1; i++) {
                    if(!prevIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex - 1, i));
                        prevIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) prevIsDigit = false;
                }
            }
        } else { // middle rows
            boolean prevBtmRowIsDigit = false;  // prevIsDigit will track top row
            if(currentColIndex == 0) {    // starting on first col
                // check 1 char to the right on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex + 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex + 1));
                // check the row above & below
                for(int i = 0; i <= currentColIndex + 1; i++) {
                    if(!prevIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex - 1, i));
                        prevIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) prevIsDigit = false;

                    if(!prevBtmRowIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex + 1, i));
                        prevBtmRowIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) prevBtmRowIsDigit = false;
                }
            } else if(currentColIndex == engineSchematic.get(currentRowIndex).length - 1) { // ending on last col
                // check 1 char to the left on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex - 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex - 1));
                // check the row above & below
                for(int i = currentColIndex - 1; i <= currentColIndex; i++) {
                    if(!prevIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex - 1, i));
                        prevIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) prevIsDigit = false;

                    if(!prevBtmRowIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex + 1, i));
                        prevBtmRowIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) prevBtmRowIsDigit = false;
                }
            } else {    // in the middle cols
                // check 1 char to the left on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex - 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex - 1));
                // check 1 char to the right on the same row
                if(Character.isDigit(engineSchematic.get(currentRowIndex)[currentColIndex + 1])) coordinates.add(new Coordinate(currentRowIndex, currentColIndex + 1));
                // check the row above & below
                for(int i = currentColIndex - 1; i <= currentColIndex + 1; i++) {
                    if(!prevIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex - 1, i));
                        prevIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex - 1)[i])) prevIsDigit = false;

                    if(!prevBtmRowIsDigit && Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) {
                        coordinates.add(new Coordinate(currentRowIndex + 1, i));
                        prevBtmRowIsDigit = true;
                    } else if(!Character.isDigit(engineSchematic.get(currentRowIndex + 1)[i])) prevBtmRowIsDigit = false;
                }
            }
        }
        return coordinates;
    }

    // To identify the full number given a single row x col index (i.e. the position of one of its digits)
    public static int identifyPartNumber(List<char[]> engineSchematic, int rowIndex, int colIndex) {
        // the number exists within the parentRow
        char[] parentRow = engineSchematic.get(rowIndex);
        StringBuilder partNumber = new StringBuilder();
        partNumber.append(parentRow[colIndex]);


        int prevCharIndex = colIndex - 1;
        while(prevCharIndex >= 0 && Character.isDigit(parentRow[prevCharIndex])) {
            partNumber.insert(0, parentRow[prevCharIndex]);
            prevCharIndex -= 1;
        }


        int nextCharIndex = colIndex + 1;
        while(nextCharIndex < parentRow.length && Character.isDigit(parentRow[nextCharIndex])) {
            partNumber.append(parentRow[nextCharIndex]);
            nextCharIndex += 1;
        }

        System.out.println("Part number: " + partNumber.toString());
        return Integer.parseInt(partNumber.toString());
    }

    public static int getSumOfPartNumbers(List<char[]> engineSchematic) {
        int total = 0;
        for(int i = 0; i < engineSchematic.size(); i++) {
            int startOfNumRowIndex = -1, endOfNumRowIndex = -1;
            char[] row = engineSchematic.get(i);
            StringBuilder partNumber = new StringBuilder();
            for(int j = 0; j < row.length; j++) {
                // find start and end of a number in a char[] row
                while(Character.isDigit(row[j])) {
                    if(startOfNumRowIndex == -1) startOfNumRowIndex = j;
                    partNumber.append(row[j]);
                    // case where number is at the end of the row - using j++ as the index would be wrong
                    endOfNumRowIndex = j;
                    if(j == row.length - 1) {
                        break;
                    } else j++;
                }
                // number detected - time to check adjacent values
                if(startOfNumRowIndex != -1 && endOfNumRowIndex != -1) {
                    // go back one (because the while loop ends by exceeding the part number by 1 position), except when the number is the last element in the row
                    if(endOfNumRowIndex != row.length - 1) j--;
                    boolean isValidPart = checkAdjacentPositions(engineSchematic, i, startOfNumRowIndex, endOfNumRowIndex);
                    if(isValidPart) total += Integer.parseInt(partNumber.toString());
                    // reset partNumber
                    partNumber = new StringBuilder();
                    // re-assign back to 'initial' val
                    startOfNumRowIndex = -1; 
                    endOfNumRowIndex = -1;
                }
            }
        }
        return total;
    }

    public static boolean checkAdjacentPositions(List<char[]> engineSchematic, int currentRowIndex, int startColIndex, int endColIndex) {
        if(currentRowIndex == 0) {  // first row
            if(startColIndex == 0) {    // starting on first col
                // check 1 char to the right on the same row
                if(isSymbol(engineSchematic.get(currentRowIndex)[endColIndex + 1])) return true;
                // check the row below
                for(int i = 0; i <= endColIndex + 1; i++) {
                    if(isSymbol(engineSchematic.get(currentRowIndex + 1)[i])) return true;
                }
            } else if(endColIndex == engineSchematic.get(currentRowIndex).length - 1) { // ending on last col
                // check 1 char to the left on the same row
                if(isSymbol(engineSchematic.get(currentRowIndex)[startColIndex - 1])) return true;
                // check the row below
                for(int i = startColIndex - 1; i <= endColIndex; i++) {
                    if(isSymbol(engineSchematic.get(currentRowIndex + 1)[i])) return true;
                }
            } else {    // in the middle cols
                // check 1 char to the left and right on the same row
                if(isSymbol(engineSchematic.get(currentRowIndex)[startColIndex - 1]) || isSymbol(engineSchematic.get(currentRowIndex)[endColIndex + 1])) return true;
                // check the row below
                for(int i = startColIndex - 1; i <= endColIndex + 1; i++) {
                    if(isSymbol(engineSchematic.get(currentRowIndex + 1)[i])) return true;
                }
            }
        } else if(currentRowIndex == engineSchematic.size() - 1) {  // last row
            if(startColIndex == 0) {    // starting on first col
                // check 1 char to the right on the same row
                if(isSymbol(engineSchematic.get(currentRowIndex)[endColIndex + 1])) return true;
                // check the row above
                for(int i = 0; i <= endColIndex + 1; i++) {
                    if(isSymbol(engineSchematic.get(currentRowIndex - 1)[i])) return true;
                }
            } else if(endColIndex == engineSchematic.get(currentRowIndex).length - 1) { // ending on last col
                // check 1 char to the left on the same row
                if(isSymbol(engineSchematic.get(currentRowIndex)[startColIndex - 1])) return true;
                // check the row above
                for(int i = startColIndex - 1; i <= endColIndex; i++) {
                    if(isSymbol(engineSchematic.get(currentRowIndex - 1)[i])) return true;
                }
            } else {    // in the middle cols
                // check 1 char to the left and right on the same row
                if(isSymbol(engineSchematic.get(currentRowIndex)[startColIndex - 1]) || isSymbol(engineSchematic.get(currentRowIndex)[endColIndex + 1])) return true;
                // check the row above
                for(int i = startColIndex - 1; i <= endColIndex + 1; i++) {
                    if(isSymbol(engineSchematic.get(currentRowIndex - 1)[i])) return true;
                }
            }
        } else { // middle rows
            if(startColIndex == 0) {    // starting on first col
                // check 1 char to the right on the same row
                if(isSymbol(engineSchematic.get(currentRowIndex)[endColIndex + 1])) return true;
                // check the row above & below
                for(int i = 0; i <= endColIndex + 1; i++) {
                    if(isSymbol(engineSchematic.get(currentRowIndex - 1)[i])) return true;
                    if(isSymbol(engineSchematic.get(currentRowIndex + 1)[i])) return true;
                }
            } else if(endColIndex == engineSchematic.get(currentRowIndex).length - 1) { // ending on last col
                // check 1 char to the left on the same row
                if(isSymbol(engineSchematic.get(currentRowIndex)[startColIndex - 1])) return true;
                // check the row above & below
                for(int i = startColIndex - 1; i <= endColIndex; i++) {
                    if(isSymbol(engineSchematic.get(currentRowIndex - 1)[i])) return true;
                    if(isSymbol(engineSchematic.get(currentRowIndex + 1)[i])) return true;
                }
            } else {    // in the middle cols
                // check 1 char to the left and right on the same row
                if(isSymbol(engineSchematic.get(currentRowIndex)[startColIndex - 1]) || isSymbol(engineSchematic.get(currentRowIndex)[endColIndex + 1])) return true;
                // check the row above & below
                for(int i = startColIndex - 1; i <= endColIndex + 1; i++) {
                    if(isSymbol(engineSchematic.get(currentRowIndex - 1)[i])) return true;
                    if(isSymbol(engineSchematic.get(currentRowIndex + 1)[i])) return true;
                }
            }
        }
        return false;
    }

    public static boolean isSymbol(char c) {
        boolean isValid = !Character.isLetter(c) && !Character.isDigit(c) && c != '.';
        // if(!isValid) {
        //     System.out.println("Symbol " + c + " is invalid!");
        // }
        return isValid;
    }

    public static void printSchematic(List<char[]> engineSchematic) {
        for(int i = 0; i < engineSchematic.size(); i++) {
            for(int j = 0; j < engineSchematic.get(i).length; j++) {
                System.out.print(engineSchematic.get(i)[j]);
            }
            System.out.println();
        }
    }
}
