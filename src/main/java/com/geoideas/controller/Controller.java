/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geoideas.controller;

import com.geoideas.model.database.Database;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.Router;

/**
 *
 * @author owen
 */
public abstract class Controller {
    private Router router;
    private Database db;
    public static final String APPLICATION_JSON = "application/json";

    public Controller(Router router, Database db) {
        this.router = router;
        this.db = db;
    }

    public abstract void addRoutes();
    
    public void send(HttpServerResponse res, JsonObject data) {
        send(res, data.encode());
    }
    
    public void send(HttpServerResponse res, JsonArray data) {
        send(res, data.encode());
    }
    
    private void send(HttpServerResponse res, String data) {
        res.putHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
                .end(data);
    }
    
    public void sendError(HttpServerResponse res, String mes, int code) {
        res.setStatusMessage(mes).setStatusCode(code).end();
    }
    
    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Database getDb() {
        return db;
    }

    public void setDb(Database db) {
        this.db = db;
    }
    
}
