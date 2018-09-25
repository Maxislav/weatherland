package com.lipatov.weatherland.dao.jdbc;

import com.lipatov.weatherland.dao.IReportDao;
import com.lipatov.weatherland.entity.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ReportDao implements IReportDao {

    @Override
    public List<Report> getAll() {
        log.info("Dao layer");
        return new ArrayList<>();
    }
}
