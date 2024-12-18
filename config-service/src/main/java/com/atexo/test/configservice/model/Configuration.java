package com.atexo.test.configservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CONFIGURATION")
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String display;
    private String prefix;
    private String suffix;
    private Integer length;
    @Column(nullable = false, unique = true)
    private Integer configOrder;
    private Integer initValue;

    public Configuration(Long id, String name, String display, String prefix, String suffix, int length, int configOrder, int initValue) {
        this.id = id;
        this.name = name;
        this.display = display;
        this.prefix = prefix;
        this.length = length;
        this.suffix = suffix;
        this.configOrder = configOrder;
        this.initValue = initValue;
    }

    public Configuration() {
    }

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

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Integer getConfigOrder() {
        return configOrder;
    }

    public void setConfigOrder(int configOrder) {
        this.configOrder = configOrder;
    }

    public int getInitValue() {
        return initValue;
    }

    public void setInitValue(int initValue) {
        this.initValue = initValue;
    }
}
