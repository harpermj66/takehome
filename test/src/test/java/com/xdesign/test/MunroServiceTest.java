package com.xdesign.test;

import com.xdesign.test.model.HillTop;
import com.xdesign.test.service.MunroService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MunroServiceTest {

	@Autowired
	private MunroService munroService;

	private static List<HillTop> hillTopList;

	@BeforeAll
	static void initData() {
		hillTopList = new ArrayList<>();
		hillTopList.add(new HillTop("D", "TOP", 4.0, "DEFG"));
		hillTopList.add(new HillTop("E", "TOP", 5.0, "PEFG"));
		hillTopList.add(new HillTop("F", "", 5.0, "PEFG"));
		hillTopList.add(new HillTop("A", "MUN", 1.0, "ABCD"));
		hillTopList.add(new HillTop("B", "TOP", 2.0, "BCDE"));
		hillTopList.add(new HillTop("C", "MUN", 3.0, "CDEF"));
	}

	@BeforeEach
	void setData() {
		this.munroService.setHillTops(hillTopList);
	}

	@Test
	void loadDataTest() {
		assert munroService.getHillTops().size() == 6;
	}

	@Test
	void filterNullTest() {
		List<HillTop> filtered = munroService.find(null,null,null,null,null);
		assert filtered.size() == 5;
	}

	@Test
	void filterCategoryTest() {
		List<HillTop> filtered = munroService.find("mun",null,null,null,null);
		assert filtered.size() == 2;
		filtered = munroService.find("top",null,null,null,null);
		assert filtered.size() == 3;
	}

	@Test
	void filterMinHeightTest() {
		List<HillTop> filtered = munroService.find(null,null,null,2.0,null);
		assert filtered.size() == 4;
	}

	@Test
	void filterMaxHeightTest() {
		List<HillTop> filtered = munroService.find(null,null,null,null,4.0);
		assert filtered.size() == 4;
	}

	@Test
	void filterMaxMinHeightTest() {
		List<HillTop> filtered = munroService.find(null,null,null,3.0,4.0);
		assert filtered.size() == 2;
	}

	@Test
	void filterCategoryMinHeightTest() {
		List<HillTop> filtered = munroService.find("top",null,null,3.0,null);
		assert filtered.size() == 2;
	}

	@Test
	void defaultSortTest() {
		List<HillTop> filtered = munroService.find(null,null,null,null,null);
		assert filtered.get(0).getName().equals("A");
	}

	@Test
	void sortNameAscendingTest() {
		List<HillTop> filtered = munroService.find(null,"name asc",null,null,null);
		assert filtered.get(0).getName().equals("A");
	}

	@Test
	void sortDescendingTest() {
		List<HillTop> filtered = munroService.find(null,"name desc",null,null,null);
		assert filtered.get(0).getName().equals("E");
	}

	@Test
	void sortHeightAscendingTest() {
		List<HillTop> filtered = munroService.find(null,"height asc",null,3.0,null);
		assert filtered.get(0).getHeightMetre() == 3.0;
	}

	@Test
	void sortHeightDescendingTest() {
		List<HillTop> filtered = munroService.find(null,"height desc",null,3.0,null);
		assert filtered.get(0).getHeightMetre() == 5.0;
	}

	@Test
	void limitResultsTest() {
		List<HillTop> filtered = munroService.find(null,"height desc",2,3.0,null);
		assert filtered.get(0).getHeightMetre() == 5.0 && filtered.size() == 2;
	}
}
