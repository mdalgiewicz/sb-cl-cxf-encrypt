package com.dalgim.example.sb.cxf.encrypt.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by dalgim on 08.04.2017.
 */
@XmlRootElement(name = "Fruit")
@XmlType(propOrder = {
        "id", "name", "protein", "carbo", "fat", "kcal"
})
public class Fruit {

    private Long id;
    private String name;
    private Double kcal;
    private Double protein;
    private Double fat;
    private Double carbo;

    @XmlElement(name = "id", nillable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getKcal() {
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getCarbo() {
        return carbo;
    }

    public void setCarbo(Double carbo) {
        this.carbo = carbo;
    }
}
