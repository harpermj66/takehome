package com.xdesign.test.service;

import com.opencsv.CSVReader;
import com.xdesign.test.model.HillTop;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Service
public class MunroService {

    private final Logger logger = LoggerFactory.getLogger(MunroService.class);

    @Value("${lookup.data.file}")
    private String dataFile;

    private List<HillTop> hillTops = new ArrayList<>();
    private Boolean hillTopsExist = false;

    public List<HillTop> find(String category, String sort, Integer size, Double minHeight, Double maxHeight) {

        List<HillTop> filteredAndSorted =
                hillTops
                .stream()
                .filter( c -> filterByCategory(category, c.getYPost1997()))
                .filter( c -> filterByHeights(minHeight, maxHeight, c.getHeightMetre()))
                .sorted( (o1, o2) -> sortValues(sort, o1, o2))
                .collect(Collectors.toList());
        if (size != null) {
            return filteredAndSorted.stream().limit(size).collect(Collectors.toList());
        } else {
            return filteredAndSorted;
        }
    }

    private Boolean filterByHeights(Double minHeight, Double maxHeight, Double currentValue) {
        if (minHeight == null && maxHeight == null) {
            return true;
        } else if (maxHeight == null) {
            return currentValue >= minHeight;
        } else if (minHeight == null) {
            return currentValue <= maxHeight;
        } else {
            return currentValue >= minHeight && currentValue <= maxHeight;
        }
    }

    private Integer sortValues(String sort, HillTop o1, HillTop o2) {
        if (sort == null || sort.length() == 0) {
            return o1.getName().compareTo(o2.getName());
        }

        // Take the first sort and if it is not name then sort by that first
        // Not implementing combined sorts as yet
        String[] tokens = sort.toLowerCase().split(",");
        for (String token : tokens) {
            String[] sortAndDirection = token.trim().split(" ");
            String fieldName = sortAndDirection[0].trim().toLowerCase();
            String direction = sortAndDirection[1].trim().toLowerCase();
            if (fieldName.equals("height")) {
                if (direction.equals("asc")) {
                    return o1.getHeightMetre().compareTo(o2.getHeightMetre());
                } else {
                    return o2.getHeightMetre().compareTo(o1.getHeightMetre());
                }
            } else {
                if (direction.equals("asc")) {
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return o2.getName().compareTo(o1.getName());
                }

            }
        }

        return 0;
    }

    private Boolean filterByCategory(String category, String value) {
        if (value == null || value.trim().length() == 0) {
            return false;
        }
        if (category == null || category.length() == 0) {
            return true;
        } else return value.trim().equalsIgnoreCase(category.trim());
    }

    @PostConstruct
    public void loadData() throws IOException {
        logger.info("Looking for file " + System.getProperty("user.dir") + "/" + dataFile);
        if (fileExists(dataFile)) {
            hillTopsExist = true;
            List<String[]> lines = loadLines(dataFile);
            loadModel(lines);
        } else {
            // Deliberately allowing spring to start rather than throwing an exception here.
            // The controller will report and error when data is searched for.
            logger.error("FATAL!!!!! - File not found : " + System.getProperty("user.dir") + "/" + dataFile);
        }
    }

    private void loadModel(List<String[]> lines) {
        lines.stream()
                .skip(1)
                .forEach(this::addLine);
    }

    private void addLine(String[] line) {

        HillTop hillTop = new HillTop();
        hillTop.setRunningNo(Integer.valueOf(line[0]));
        hillTop.setDobihNumber(Integer.valueOf(line[1]));
        hillTop.setStreetMap(line[2]);
        hillTop.setGeograph(line[3]);
        hillTop.setHillBagging(line[4]);
        hillTop.setName(line[5]);
        hillTop.setSmcSection(Integer.parseInt(line[6]));
        hillTop.setRhbSection(line[7]);
        hillTop.setSection(Double.parseDouble(line[8]));
        hillTop.setHeightMetre(Double.parseDouble(line[9]));
        hillTop.setHeightFeet(Double.parseDouble(line[10]));
        hillTop.setMap150(line[11]);
        hillTop.setMap125(line[12]);
        hillTop.setGridRef(line[13]);
        hillTop.setGridRefXY(line[14]);
        hillTop.setXcoord(Long.parseLong(line[15]));
        hillTop.setYcoord(Long.parseLong(line[16]));
        hillTop.setY1891(line[17]);
        hillTop.setY1921(line[18]);
        hillTop.setY1933(line[19]);
        hillTop.setY1953(line[20]);
        hillTop.setY1969(line[21]);
        hillTop.setY1974(line[22]);
        hillTop.setY1981(line[23]);
        hillTop.setY1984(line[24]);
        hillTop.setY1990(line[25]);
        hillTop.setY1997(line[26]);
        hillTop.setYPost1997(line[27]);
        hillTop.setComments(line[28]);

        hillTops.add(hillTop);
    }

    private List<String[]> loadLines(String dataFileName) throws IOException {
        Reader reader = Files.newBufferedReader(
                Paths.get(System.getProperty("user.dir") + "/" + dataFileName));
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> lines = csvReader.readAll();
        reader.close();
        csvReader.close();
        return lines;
    }

    private Boolean fileExists(String dataFileName) {
        return new File(System.getProperty("user.dir") + "/" + dataFileName).exists();
    }

}
