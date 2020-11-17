package com.xdesign.test;

import com.xdesign.test.controller.HillTopController;
import com.xdesign.test.model.HillTop;
import com.xdesign.test.service.MunroService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class HillTopControllerTest {

    @Autowired
    private HillTopController hillTopController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MunroService munroService;

    private static List<HillTop> hillTopList;

    @BeforeAll
    static void initData() {
        hillTopList = new ArrayList<>();
        hillTopList.add(new HillTop("D", "TOP", 4.0, "DEFG"));
    }

    @Test
    void testEmptyData() throws Exception {
        this.mockMvc
                .perform(get("/api/munro"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNonNullData() throws Exception {
        this.munroService.setHillTops(hillTopList);
        this.mockMvc
                .perform(get("/api/munro"))
                .andExpect(status().isOk());
    }

    @Test
    void testMinMaxHeightFail() throws Exception {
        this.munroService.setHillTops(hillTopList);
        this.mockMvc
                .perform(get("/api/munro?minHeight=3&maxHeight=2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMinMaxHeightOK() throws Exception {
        this.munroService.setHillTops(hillTopList);
        this.mockMvc
                .perform(get("/api/munro?minHeight=2&maxHeight=3"))
                .andExpect(status().isOk());
    }

    @Test
    void testSortFail1() throws Exception {
        this.munroService.setHillTops(hillTopList);
        this.mockMvc
                .perform(get("/api/munro?sort=number"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSortFail2() throws Exception {
        this.munroService.setHillTops(hillTopList);
        this.mockMvc
                .perform(get("/api/munro?sort=height asc, name des"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSortFail3() throws Exception {
        this.munroService.setHillTops(hillTopList);
        this.mockMvc
                .perform(get("/api/munro?sort=height asceding"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSortPass1() throws Exception {
        this.munroService.setHillTops(hillTopList);
        this.mockMvc
                .perform(get("/api/munro?sort=height asc"))
                .andExpect(status().isOk());
    }

    @Test
    void testSortPass2() throws Exception {
        this.munroService.setHillTops(hillTopList);
        this.mockMvc
                .perform(get("/api/munro?sort=height asc, name desc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSortFieldNameFail1() throws Exception {
        this.munroService.setHillTops(hillTopList);
        this.mockMvc
                .perform(get("/api/munro?sort=number asc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSortFieldNamePass1() throws Exception {
        this.munroService.setHillTops(hillTopList);
        this.mockMvc
                .perform(get("/api/munro?sort=name asc, height asc"))
                .andExpect(status().isBadRequest());
    }
}
