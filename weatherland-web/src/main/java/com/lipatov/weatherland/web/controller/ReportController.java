package com.lipatov.weatherland.web.controller;

import com.lipatov.weatherland.entity.Report;
import com.lipatov.weatherland.service.IReportService;

import jdk.nashorn.internal.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

//@RestController
// @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//@Slf4j
@Controller
public class ReportController {


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

        JSONObject jsonObj = readLineByLineJava8(System.getProperty("user.dir")+"/2018-09-21_09-47-04.900.json");
        //JSONObject jsonObj = new JSONObject();

        Iterator<String> keys = jsonObj.keys();

        while(keys.hasNext()) {
            String key = keys.next();


            try {
                if (jsonObj.get(key) instanceof JSONObject) {

                }else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return   "index";
    }



    private  JSONObject readLineByLineJava8(String filePath)
    {
        JSONObject jsonObj = null;
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s));
        }
        catch (IOException e)
        {
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
