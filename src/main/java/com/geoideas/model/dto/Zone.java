/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geoideas.model.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author owen
 */
public class Zone {
    
    @NotNull(message = "latitude cannot be null")
    @DecimalMin(value = "0.1", message = "laitude must be greater than 0")
    private double latitude;
    
    @NotNull(message = "longitude cannot be null")
    @DecimalMin(value = "0.1", message = "longitude must be greater than 0")
    private double longitude;
    
    @NotNull(message = "radius cannot be null")
    @Min(value = 30, message = "radius must be greater than 30")
    private int radius;
    
    @NotBlank(message = "name cannot be empty")
    private String name;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
