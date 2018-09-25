package com.lipatov.weatherland.web.controller;

import com.lipatov.weatherland.entity.Report;
import com.lipatov.weatherland.service.IReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class ReportController {

    @Autowired
    private IReportService reportService;

    @RequestMapping(value = "/report")
    public List<Report> getAll() {
        log.info("Sending request to get all reports");
        List<Report> reports = reportService.getAll();
        reports.add(Report.builder().id("1").name("name_1").build());
        reports.add(Report.builder().id("2").name("name_2").build());
        return reports;
    }
}
