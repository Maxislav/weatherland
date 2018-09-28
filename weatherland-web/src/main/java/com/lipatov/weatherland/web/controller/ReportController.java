package com.lipatov.weatherland.web.controller;

import com.lipatov.weatherland.entity.Report;
import com.lipatov.weatherland.service.IReportService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

        model.put("message", this.message);
        model.put("index", id);
        return   "index";
    }
}
