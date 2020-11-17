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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/munro")
public class HillTopController {

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
        } else if (sort != null && !SortValidator.canParseSort(sort)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sort parameter must be in format field1 asc, field2 desc etc");
        } else if (sort != null && !SortValidator.sortContainsIllegalFields(sort)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal sort fields, field must be height or name");
        }
        // Convert to dto
        return munroService.find(category, sort, size, minHeight, maxHeight)
                .stream()
                .map(HillTop::toDTO)
                .collect(Collectors.toList());
    }

}
