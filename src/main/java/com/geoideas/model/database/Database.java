/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geoideas.model.database;

import com.geoideas.model.dto.Zone;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.mongo.MongoClient;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author owen
 */
public class Database {
    
    private final String DOCUMENT = "zones";
    private MongoClient db;
    
    public Database(MongoClient db) {
        this.db = db;
    }

    public JsonObject createZone(Zone data) {
        var json = JsonObject.mapFrom(data);
        return db.rxSave(DOCUMENT, json)
                .flatMap(id -> {
                    var filter = new JsonObject().put("_id", id);
                    return db.rxFindOne(DOCUMENT, filter, new JsonObject());
                }).blockingGet();
                
    }
    
    public long deleteZone(String id) {
        var query = new JsonObject().put("_id", id);
        return db.rxRemoveDocument(DOCUMENT, query)
                .blockingGet()
                .getRemovedCount();
    }
    
    public JsonArray readAllZones() {
        return db.rxFind(DOCUMENT, new JsonObject())
                .map(JsonArray::new)
                .blockingGet();
                
    }
    
    public MongoClient getDb() {
        return db;
    }

    public void setDb(MongoClient db) {
        this.db = db;
    }
        
}
