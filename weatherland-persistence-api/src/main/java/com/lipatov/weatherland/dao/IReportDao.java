package com.lipatov.weatherland.dao;

import com.lipatov.weatherland.entity.Report;

import java.util.List;

public interface IReportDao {

    List<Report> getAll();
}
