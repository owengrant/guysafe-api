package com.geoideas;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.geoideas.controller.ZoneController;
import com.geoideas.model.database.Database;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.mongo.MongoClient;
import io.vertx.reactivex.ext.web.Router;

public class Gateway extends AbstractVerticle {

    @Override
    public void start() {
        startServer();
    }
    
    private void startServer() {
        var router = addRoutes();
        var serverConfig = config().getJsonObject("server");
        var host = serverConfig.getString("host");
        var port = serverConfig.getInteger("port");
        var options = new HttpServerOptions()
                .setHost(host)
                .setPort(port);
        vertx.createHttpServer(options)
                .requestHandler(router)
                .listen(result -> {
                   if(result.succeeded()) 
                       System.out.println("Server running");
                   else result.cause().printStackTrace();
                });
        // ignore unknown values from body
        Json.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);        
    }

    private Router addRoutes() {
        var router = Router.router(vertx);
        var db = createDatabase();
        var zoneController = new ZoneController(router, db);
        zoneController.addRoutes();
        return router;
    }
    
    private Database createDatabase() {
        var config = config().getJsonObject("database");
        return new Database(MongoClient.createShared(vertx, config));
    }
    
}
