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
    
    private String _id;
    
    @NotNull(message = "latitude cannot be null")
    private double latitude;
    
    @NotNull(message = "longitude cannot be null")
    private double longitude;
    
    @NotNull(message = "radius cannot be null")
    @Min(value = 10, message = "radius must be greater than 9")
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

    public String getId() {
        return _id;
    }

    public Zone setId(String _id) {
        this._id = _id;
        return this;
    }
    
    
}
