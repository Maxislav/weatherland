package com.lipatov.weatherland.web.controller;

import com.lipatov.weatherland.entity.Report;
import com.lipatov.weatherland.service.IReportService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@RestController
// @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//@Slf4j
@Controller
public class ReportController {


    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    // @Value("${index.message:test}")
    private String message = "Hello World";

    @Autowired
    private IReportService reportService;

    @RequestMapping(value = "/report")
    public List<Report> getAll() {
        // log.info("Sending request to get all reports");
        List<Report> reports = reportService.getAll();
        reports.add(Report.builder().id("1").name("name_1").build());
        reports.add(Report.builder().id("2").name("name_2").build());
        return reports;
    }


    @RequestMapping(value = "/report/{id}")
    public String getReportById(@PathVariable String id, Map<String, Object> model) {

       /* log.info("Sending request to get all reports, movieId from request: {}", id);
        List<Report> reports = reportService.getAll();
        reports.add(Report.builder().id("1").name("name_1").build());
        reports.add(Report.builder().id("2").name("name_2").build());*/


        // System.getProperty("user.dir");
        model.put("message", this.message);
        model.put("index", id);
        model.put("dir", System.getProperty("user.dir"));
        JSONObject jsonObject = readLineByLineJava8(System.getProperty("user.dir") + "/2018-09-21_09-47-04.900.json");
        String content = htmlFromJson(jsonObject);
        model.put("content", content);
        return "index";
    }


    private String htmlFromArray(JSONArray jsonArray) {
        StringBuilder contentBuilder = new StringBuilder();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object v = null;
            try {
                v = jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (v instanceof JSONArray) {
                contentBuilder.append("<div>" + htmlFromArray((JSONArray) v) + "</div>");
            } else if (v instanceof JSONObject) {
                contentBuilder.append("<div>" + htmlFromJson((JSONObject) v) + "</div>");
            } else {
                contentBuilder.append("<div>" + String.valueOf(v) + "</div>");
            }

        }
        return contentBuilder.toString();
    }

    private String htmlFromJson(JSONObject jsonObject) {
        Iterator<String> keys = jsonObject.keys();
        StringBuilder contentBuilder = new StringBuilder();
        while (keys.hasNext()) {
            String key = keys.next();
            Object v = null;
            try {
                v = jsonObject.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (v instanceof JSONArray) {
                contentBuilder.append("<div class='row'> " +
                        "<div class='flex'>" +
                        "<div class='key'>" + key + "</div>" +
                        "<div class='value'>" + htmlFromArray((JSONArray) v) + "</div>" +
                        "</div>" +
                        "</div>"
                );

            } else if (v instanceof JSONObject) {
                contentBuilder.append(
                        "<div class='row'> " +
                                "<div class='flex'>" +
                                "<div class='key'>" + key + "</div>" +
                                "<div class='value'>" + htmlFromJson((JSONObject) v) + "</div>" +
                                "</div>" +
                                "</div>"
                );
            } else {
                contentBuilder.append(
                        "<div class='row'> " +
                                "<div class='flex'>" +
                                "<div class='key'>" + key + "</div>" +

                                "<div class='value'>" + String.valueOf(v) + "</div>" +
                                "</div>" +
                                "</div>"
                );
            }

        }

        return contentBuilder.toString();
    }

    private JSONObject readLineByLineJava8(String filePath) {
        JSONObject jsonObj = null;
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = contentBuilder.toString();
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

}
