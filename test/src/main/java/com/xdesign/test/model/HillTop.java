package com.xdesign.test.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HillTop {

    private Integer runningNo;
    private Integer dobihNumber;
    private String streetMap;
    private String geograph;
    private String hillBagging;
    private String name;
    private Integer smcSection;
    private String rhbSection;
    private Double section;
    private Double heightMetre;
    private Double heightFeet;
    private String map150;
    private String map125;
    private String gridRef;
    private String gridRefXY;
    private Long xcoord;
    private Long ycoord;
    private String y1891;
    private String y1921;
    private String y1933;
    private String y1953;
    private String y1969;
    private String y1974;
    private String y1981;
    private String y1984;
    private String y1990;
    private String y1997;
    private String yPost1997;
    private String comments;

    public HillTop() {

    }

    public HillTop(String name, String category, Double height, String gridReference) {
        this.name = name;
        this.yPost1997 = category;
        this.heightMetre = height;
        this.gridRef = gridReference;
    }

    public HillTopDTO toDTO() {
        HillTopDTO dto = new HillTopDTO();
        dto.setName(name);
        dto.setCategory(yPost1997);
        dto.setGridReference(gridRef);
        dto.setHeight(heightMetre);
        return dto;
    }
}
