package com.ptc.xla.config;

import static spark.Spark.get;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.TemplateEngine;

import com.ptc.xla.services.LoginService;

/**
 * Created by ssinghal
 * Created on 25-Jan-2016
 * If you refactor this code, remember: Code so clean you could eat off it!
 */
public class WebConfig {

    private TemplateEngine engine;
    private LoginService loginService;

    public WebConfig(LoginService loginService) {
        engine = new XlaThymeleafTemplateEngine();
        this.loginService = loginService;
        setupRoutes();
    }

    private void setupRoutes() {

        get("/hello", (req, res) -> {
            return "Hello World!";
        });

//        get("")

//        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
//        Configuration freeMarkerConfiguration = new Configuration();
//        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(BlogService.class, "/"));
//        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);



        get("/login", (req, res) -> {
            Map<String, Object> model = getModel(req);
            return new ModelAndView(model, loginService.getUserName());
        }, engine);

        get("/summary", (req, res) -> {
            Map<String, Object> model = getModel(req);
            return new ModelAndView(model, "summary");
        }, engine);

    }

    private Map<String, Object> getModel(Request req) {
        Map<String, Object> model = new HashMap<>();
        Locale locale = req.session().attribute("user-locale");
        model.put("locale", locale == null ? Locale.ENGLISH : locale);
        return model;
    }
}
