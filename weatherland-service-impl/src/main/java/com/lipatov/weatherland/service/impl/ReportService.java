package com.lipatov.weatherland.service.impl;

import com.lipatov.weatherland.dao.IReportDao;
import com.lipatov.weatherland.entity.Report;
import com.lipatov.weatherland.service.IReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReportService implements IReportService {

    @Autowired
    private IReportDao reportDao;

    @Override
    public List<Report> getAll() {
        log.info("Service layer");
        reportDao.getAll();
        return new ArrayList<>();
    }
}
