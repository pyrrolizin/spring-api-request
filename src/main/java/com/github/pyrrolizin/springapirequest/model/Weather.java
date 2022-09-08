package com.github.pyrrolizin.springapirequest.model;

import java.io.Serializable;

public class Weather implements Serializable {
    private final float temperature;
    private final String condition;

    public Weather(String condition, float temperature) {
        this.condition = condition;
        this.temperature = temperature;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return condition;
    }
}
