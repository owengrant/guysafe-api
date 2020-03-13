/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geoideas.controller;


import com.geoideas.model.database.Database;
import com.geoideas.model.dto.Zone;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import java.util.stream.Collectors;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author owen
 */
public class ZoneController extends Controller {
    
  
    public ZoneController(Router router, Database db) {
        super(router, db);
    }
    
    @Override
    public void addRoutes() {
        var router = getRouter();
        router.errorHandler(500, errorHandler -> {
           errorHandler.failure().printStackTrace();
        });
        router.route("/*").handler(BodyHandler.create());
        router.post("/api/v1/zones")
                .consumes(APPLICATION_JSON)
                .produces(APPLICATION_JSON)
                .blockingHandler(this::create);
        router.get("/api/v1/zones")
                .produces(APPLICATION_JSON)
                .blockingHandler(this::readAll);
        router.delete("/api/v1/zones/:id")
                .produces(APPLICATION_JSON)
                .blockingHandler(this::delete);
    }
    
    private void create(RoutingContext ctx) {
        var data = ctx.getBodyAsJson();
        var zone = data.mapTo(Zone.class);
        // validation on behalf of the model
        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        var errors = validator.validate(zone);
        if(errors.size() > 0) {
            var errorMessages = errors.stream()
                    .map(error -> error.getMessage())
                    .collect(Collectors.toList())
                    .toString();
            sendError(ctx.response(), errorMessages, 400);
            return;
        }
        var result = getDb().createZone(zone);
        send(ctx.response(), result);
    }
    
    private void readAll(RoutingContext ctx) {
        send(ctx.response(), getDb().readAllZones());
    }
    
    private void delete(RoutingContext ctx) {
        var id = ctx.pathParam("id");
        if(getDb().deleteZone(id) > 0)
            ctx.response().setStatusCode(204).end();
        else sendError(ctx.response(), "zone with id " +id+ " not found", 404);
    }
    
}
