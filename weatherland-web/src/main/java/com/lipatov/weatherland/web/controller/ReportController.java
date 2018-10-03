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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    public String getAll( Map<String, Object> model) {
        // log.info("Sending request to get all reports");
        List<String> reports = reportService.getAll();
       // reports.add(Report.builder().id("1").name("name_1").build());
       // reports.add(Report.builder().id("2").name("name_2").build());
        model.put("list", reports);
        return "app-list";
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
        //JSONObject jsonObject = readLineByLineJava8(System.getProperty("user.dir") + "/2018-09-21_09-47-04.900.json");
        //String content = htmlFromJson(jsonObject);
        //model.put("content", content);
        model.put("content", reportService.getHtmlStr());
        //logger.info(content);
        return "index";
    }




}
