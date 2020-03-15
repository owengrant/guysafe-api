/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geoideas.controller;

import com.geoideas.model.dto.Zone;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author owen
 */
public class GeoJsonUtil {
    
    public JsonObject toGeoJson(JsonArray zones) {
        Function<JsonObject, JsonArray> extractGeo = 
                (JsonObject zone) -> new JsonArray()
                        .add(zone.getDouble("longitude"))
                        .add(zone.getDouble("latitude"));
        var features = zones.stream().map(zone -> (JsonObject) zone)
                .map( zone -> 
                    new JsonObject()
                            .put("type", "Feature")
                            .put("geometry", new JsonObject()
                                    .put("type", "Point")
                                    .put("coordinates", extractGeo.apply(zone))
                            ).put("properties", zone)
                ).collect(Collectors.toList());
        return new JsonObject()
                .put("type", "FeatureCollection")
                .put("features", features);
    }
}
