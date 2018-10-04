package com.lipatov.weatherland.service;

import com.lipatov.weatherland.entity.Report;

import java.util.List;

public interface IReportService {

    List<String> getAll();
    List<String> getFileList(String appName);
    String getHtmlStr();

}
