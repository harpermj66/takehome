package com.xdesign.test.controller;

import com.xdesign.test.model.HillTop;
import com.xdesign.test.model.HillTopDTO;
import com.xdesign.test.service.MunroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/munro")
public class HillTopController {

    private static final Set<String> allowedSortFields = new HashSet<>(Arrays.asList("height", "name"));
    private static final Set<String> allowedDirections = new HashSet<>(Arrays.asList("asc", "desc"));

    private final MunroService munroService;

    @Autowired
    public HillTopController(final MunroService munroService) {
        this.munroService = munroService;
    }

    @GetMapping
    public List<HillTopDTO> findHillTops(
            @RequestParam(name="category", required = false) String category,
            @RequestParam(name="sort", required = false) String sort,
            @RequestParam(name="size", required = false) Integer size,
            @RequestParam(name="minHeight", required = false) Double minHeight,
            @RequestParam(name="maxHeight", required = false) Double maxHeight
    ) {
        // throw appropriate exceptions
        if (munroService.getHillTops().size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hilltops data is empty");
        } else if (minHeight != null && maxHeight != null && minHeight > maxHeight) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum height must be less than maximum height");
        } else if (sort != null && !canParseSort(sort)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sort parameter must be in format field1 asc, field2 desc etc");
        } else if (sort != null && !sortContainsIllegalFields(sort)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal sort fields, field must be height or name");
        }
        return munroService.getHillTops().stream().map(HillTop::toDTO).collect(Collectors.toList());
    }


    private Boolean sortContainsIllegalFields(String sort) {
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

    private Boolean canParseSort(String sort) {
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
