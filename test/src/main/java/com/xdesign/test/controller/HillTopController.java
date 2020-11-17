package com.xdesign.test.controller;

import com.xdesign.test.model.HillTop;
import com.xdesign.test.model.HillTopDTO;
import com.xdesign.test.service.MunroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/munro")
public class HillTopController {

    private final MunroService munroService;

    @Autowired
    public HillTopController(final MunroService munroService) {
        this.munroService = munroService;
    }

    @GetMapping
    public List<HillTopDTO> findHillTops() {
        return null;
    }
}
