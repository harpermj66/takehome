package com.xdesign.test.service;

import com.opencsv.CSVReader;
import com.xdesign.test.model.HillTop;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class MunroService {

    private final Logger logger = LoggerFactory.getLogger(MunroService.class);

    @Value("${lookup.data.file}")
    private String dataFile;

    private List<HillTop> hillTops = new ArrayList<>();
    private Boolean hillTopsExist = false;

    @PostConstruct
    private void loadData() throws IOException {
        logger.info("Looking for file " + System.getProperty("user.dir") + "/" + dataFile);
        if (fileExists(dataFile)) {
            hillTopsExist = true;
            List<String[]> lines = loadLines(dataFile);
            loadModel(lines);
        } else {
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
