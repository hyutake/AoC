package AoC.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day5 {
    public static void main(String[] args) {
        // runPartOne(false);
        runPartTwo(false);
    }

    // store all the info on seed mappings
    public static List<MappedValue> seedToSoilMap = new ArrayList<>();
    public static List<MappedValue> soilToFertilizerMap = new ArrayList<>();
    public static List<MappedValue> fertilizerToWaterMap = new ArrayList<>();
    public static List<MappedValue> waterToLightMap = new ArrayList<>();
    public static List<MappedValue> lightToTempMap = new ArrayList<>();
    public static List<MappedValue> tempToHumidityMap = new ArrayList<>();
    public static List<MappedValue> humidityToLocationMap = new ArrayList<>();


    public static void runPartOne(boolean debug) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the inputs below:");
        List<Double> seeds = new ArrayList<>();
        int lineCounter = 1;
        int mapId = 0;
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            // blank line
            if(s.replaceAll("\\s+", "").length() == 0) {    
                if(debug) System.out.println("Skipping line " + lineCounter++ + "!");
                continue;
            }
            if(debug) System.out.println("Checking line " + lineCounter);
            
            if(s.equalsIgnoreCase("quit")) break;
            // first line
            if(lineCounter == 1) {
                // split by any amount of whitespace
                String[] seedsArray = s.split("\\s+");

                for(int i = 1; i < seedsArray.length; i++) {
                    seeds.add(Double.parseDouble(seedsArray[i]));
                }

                System.out.println("Seed array populated!");
            } else if(s.contains("map")) {   // lines with the map description - switch to next mapper
                System.out.println(s);
                mapId++;
            } else {
                // update almanac somehow
                switch(mapId) {
                    // use seedToSoil
                    case 1:
                        updateMapper(seedToSoilMap, s);
                        break;
                    // use soilToFert
                    case 2:
                        updateMapper(soilToFertilizerMap, s);
                        break;
                    // use fertToWater
                    case 3:
                        updateMapper(fertilizerToWaterMap, s);
                        break;
                    // use waterToLight
                    case 4:
                        updateMapper(waterToLightMap, s);
                        break;
                    // use lightToTemp
                    case 5:
                        updateMapper(lightToTempMap, s);
                        break;
                    // use tempToHum
                    case 6:
                        updateMapper(tempToHumidityMap, s);
                        break;
                    // use humToLoc
                    case 7:
                        updateMapper(humidityToLocationMap, s);
                        break;
                    default:
                        System.out.println("Invalid map id of " + mapId + "!");
                        break;
                }
            }
            lineCounter++;
        }

        if(debug) {
            System.out.print("=== Seeds: ");
            printArrayList(seeds);
            
            System.out.println("=== Mappers: ");
            printMapper(seedToSoilMap, "seedToSoilMap");
            printMapper(soilToFertilizerMap, "soilToFertilizerMap");
            printMapper(fertilizerToWaterMap, "fertilizerToWaterMap");
            printMapper(waterToLightMap, "waterToLightMap");
            printMapper(lightToTempMap, "lightToTempMap");
            printMapper(tempToHumidityMap, "tempToHumidityMap");
            printMapper(humidityToLocationMap, "humidityToLocationMap");
        }

        double minLocation = Double.MAX_VALUE;

        for(double seed: seeds) {
            double soil = getMappedValueFromMapper(seedToSoilMap, seed);
            double fert = getMappedValueFromMapper(soilToFertilizerMap, soil);
            double water = getMappedValueFromMapper(fertilizerToWaterMap, fert);
            double light = getMappedValueFromMapper(waterToLightMap, water);
            double temp = getMappedValueFromMapper(lightToTempMap, light);
            double hum = getMappedValueFromMapper(tempToHumidityMap, temp);
            double loc = getMappedValueFromMapper(humidityToLocationMap, hum);
            if(debug) {
                System.out.println("Soil: " + soil);
                System.out.println("Fert: " + fert);
                System.out.println("Water: " + water);
                System.out.println("Light: " + light);
                System.out.println("Temp: " + temp);
                System.out.println("Humidity: " + hum);
                System.out.println("Location: " + loc);
            }
            if(minLocation > loc) minLocation = loc;
        }
        
        System.out.println("Lowest location num: " + minLocation);
    }

    // need to adjust logic w.r.t seed values
    // took like 2 min to calc :/
    public static void runPartTwo(boolean debug) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the inputs below:");
        List<MappedValue> seeds = new ArrayList<>();
        int lineCounter = 1;
        int mapId = 0;
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            // blank line
            if(s.replaceAll("\\s+", "").length() == 0) {    
                if(debug) System.out.println("Skipping line " + lineCounter++ + "!");
                continue;
            }
            if(debug) System.out.println("Checking line " + lineCounter);
            
            if(s.equalsIgnoreCase("quit")) break;
            // first line
            if(lineCounter == 1) {
                // split by any amount of whitespace
                String[] seedsArray = s.split("\\s+");
                double startIndex = 0, range = 0;
                for(int i = 1; i < seedsArray.length; i++) {
                    if(i % 2 == 0) {    // even indexed values are now considered as range
                        range = Double.parseDouble(seedsArray[i]);
                        seeds.add(new MappedValue(startIndex, range));
                    } else {
                        startIndex = Double.parseDouble(seedsArray[i]);
                    }
                }

                System.out.println("Seed array populated!");
            } else if(s.contains("map")) {   // lines with the map description - switch to next mapper
                System.out.println(s);
                mapId++;
            } else {
                // update almanac somehow
                switch(mapId) {
                    // use seedToSoil
                    case 1:
                        updateMapper(seedToSoilMap, s);
                        break;
                    // use soilToFert
                    case 2:
                        updateMapper(soilToFertilizerMap, s);
                        break;
                    // use fertToWater
                    case 3:
                        updateMapper(fertilizerToWaterMap, s);
                        break;
                    // use waterToLight
                    case 4:
                        updateMapper(waterToLightMap, s);
                        break;
                    // use lightToTemp
                    case 5:
                        updateMapper(lightToTempMap, s);
                        break;
                    // use tempToHum
                    case 6:
                        updateMapper(tempToHumidityMap, s);
                        break;
                    // use humToLoc
                    case 7:
                        updateMapper(humidityToLocationMap, s);
                        break;
                    default:
                        System.out.println("Invalid map id of " + mapId + "!");
                        break;
                }
            }
            lineCounter++;
        }

        if(debug) {
            System.out.print("=== Seeds: ");
            printArrayList(seeds);
            
            System.out.println("=== Mappers: ");
            printMapper(seedToSoilMap, "seedToSoilMap");
            printMapper(soilToFertilizerMap, "soilToFertilizerMap");
            printMapper(fertilizerToWaterMap, "fertilizerToWaterMap");
            printMapper(waterToLightMap, "waterToLightMap");
            printMapper(lightToTempMap, "lightToTempMap");
            printMapper(tempToHumidityMap, "tempToHumidityMap");
            printMapper(humidityToLocationMap, "humidityToLocationMap");
        }

        double minLocation = Double.MAX_VALUE;

        for(MappedValue val: seeds) {
            for(double i = 0; i < val.getRange(); i++) {
                double seed = val.getSrc() + i;
                double soil = getMappedValueFromMapper(seedToSoilMap, seed);
                double fert = getMappedValueFromMapper(soilToFertilizerMap, soil);
                double water = getMappedValueFromMapper(fertilizerToWaterMap, fert);
                double light = getMappedValueFromMapper(waterToLightMap, water);
                double temp = getMappedValueFromMapper(lightToTempMap, light);
                double hum = getMappedValueFromMapper(tempToHumidityMap, temp);
                double loc = getMappedValueFromMapper(humidityToLocationMap, hum);
                if(debug) {
                    System.out.println("Soil: " + soil);
                    System.out.println("Fert: " + fert);
                    System.out.println("Water: " + water);
                    System.out.println("Light: " + light);
                    System.out.println("Temp: " + temp);
                    System.out.println("Humidity: " + hum);
                    System.out.println("Location: " + loc);
                }
                if(minLocation > loc) minLocation = loc;
            }
            if(debug) System.out.println("Seed checked: " + val.toString());
        }
        
        System.out.println("Lowest location num: " + minLocation);
    }

    
    public static double getMappedValueFromMapper(List<MappedValue> mapper, double seed) {
        double result = seed;
        for(MappedValue val: mapper) {
            double lastAllowedSrcIndex = val.getSrc() + val.getRange() - 1;
            if(seed >= val.getSrc() && seed <= lastAllowedSrcIndex) {
                // seed has a declared source to dest mapping
                result = seed + val.getDest() - val.getSrc();
                return result;
            }
        }
        return result;
    }

    public static void updateMapper(List<MappedValue> mapper, String s) {
        // Str format: "<destination start index> <source start index> <range>"
        String[] values = s.split(" ");
        double destStartIndex = Double.parseDouble(values[0]);
        double srcStartIndex = Double.parseDouble(values[1]);
        double range = Double.parseDouble(values[2]);

        mapper.add(new MappedValue(srcStartIndex, destStartIndex, range));
    }

    public static void printMapper(List<MappedValue> mapper, String mapperName) {
        System.out.println(mapperName + ": ");
        for(MappedValue entry: mapper) {
            System.out.println(entry.toString());
        }
        System.out.println("");
    }

    // Helper fns
    public static void printArrayList(List ar) {
        for(int i = 0; i < ar.size(); i++) {
            if(i > 0) System.out.print(" ");
            System.out.print(ar.get(i).toString());
        }
        System.out.println();
        return;
    }
}

/* 
seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4
*/