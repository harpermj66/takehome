package com.xdesign.test.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// A helper class that could be expanded to provide additional sorting options
public class SortValidator {

    private static final Set<String> allowedSortFields = new HashSet<>(Arrays.asList("height", "name"));
    private static final Set<String> allowedDirections = new HashSet<>(Arrays.asList("asc", "desc"));

    public static Boolean sortContainsIllegalFields(String sort) {
        String[] tokens = sort.toLowerCase().split(",");
        for (String token : tokens) {
            String[] sortAndDirection = token.trim().split(" ");
            String fieldName = sortAndDirection[0].trim();
            if (!allowedSortFields.contains(fieldName)) {
                return false;
            }
        }
        return true;
    }

    public static Boolean canParseSort(String sort) {
        String[] tokens = sort.toLowerCase().split(",");
        if (tokens.length == 0) {
            return false;
        }

        for (String token : tokens) {
            String[] sortAndDirection = token.trim().split(" ");
            if (sortAndDirection.length != 2) {
                return false;
            }

            String direction = sortAndDirection[1].trim();
            if (!allowedDirections.contains(direction)) {
                return false;
            }
        }

        return true;
    }
}
