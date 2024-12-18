package com.atexo.test.configservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ConfigurationRequest {
    @NotBlank(message = "Field name is required")
    private String name;
    private String display;
    private String prefix;
    private String suffix;

    @Min(0)
    private int length;

    @NotNull(message = "Field order is required")
    @Min(1)
    private int configOrder;
    @Min(0)
    private int initValue;

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

    public int getConfigOrder() {
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