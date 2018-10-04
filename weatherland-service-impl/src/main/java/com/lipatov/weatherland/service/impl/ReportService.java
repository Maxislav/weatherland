package com.lipatov.weatherland.service.impl;

import com.lipatov.weatherland.dao.IReportDao;
import com.lipatov.weatherland.entity.Report;
import com.lipatov.weatherland.service.IReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Service
@Slf4j
public class ReportService implements IReportService {

    @Autowired
    private IReportDao reportDao;

    @Override
    public List<String> getAll() {
        log.info("Service layer");
        reportDao.getAll();
        File aDirectory = new File(System.getProperty("user.dir")+"/report");
        String[] filesInDir = aDirectory.list();
        List<String> fileList =  new ArrayList<>();
        for(String name: filesInDir){
            fileList.add(name);
        }
        return fileList;
    }

    @Override
    public List<String> getFileList(String appName) {
        File aDirectory = new File(System.getProperty("user.dir")+"/report/"+appName);
        String[] filesInDir = aDirectory.list();
        List<String> fileList =  new ArrayList<>();
        for(String name: filesInDir){
            fileList.add(name);
        }
        return fileList;
    }

    @Override
    public String getHtmlStr() {
        JSONObject jsonObject = readLineByLineJava8(System.getProperty("user.dir") + "/2018-09-21_09-47-04.900.json");
        return htmlFromJson(jsonObject, null);
    }

    private String htmlFromArray(JSONArray jsonArray, Integer childIndex) {
        StringBuilder contentBuilder = new StringBuilder();
        childIndex = childIndex != null ? childIndex : 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            Object v = null;
            try {
                v = jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (v instanceof JSONArray) {
                contentBuilder.append("<div>" + htmlFromArray((JSONArray) v, childIndex + 1) + "</div>");
            } else if (v instanceof JSONObject) {
                contentBuilder.append("<div>" + htmlFromJson((JSONObject) v, childIndex + 1) + "</div>");
            } else {
                contentBuilder.append("<div class='value'>" + String.valueOf(v) + "</div>");
            }

        }
        return contentBuilder.toString();
    }

    private String htmlFromJson(JSONObject jsonObject, Integer childIndex) {
        Iterator<String> keys = jsonObject.keys();
        childIndex = childIndex != null ? childIndex : 0;
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
                contentBuilder.append(
                        "<div class='row' child-index=" + String.valueOf(childIndex) + "> " +
                                "<div class='flex'>" +
                                "<div class='key'>" + key + "</div>" +
                                "<div class='cell'>" + htmlFromArray((JSONArray) v, childIndex + 1) + "</div>" +
                                "</div>" +
                                "</div>"
                );

            } else if (v instanceof JSONObject) {
                contentBuilder.append(
                        "<div class='row' child-index=" + String.valueOf(childIndex) + "> " +
                                "<div class='flex'>" +
                                "<div class='key obj'><div>" + key + "</div></div>" +
                                "<div class='cell'>" + htmlFromJson((JSONObject) v, childIndex + 1) + "</div>" +
                                "</div>" +
                                "</div>"
                );
            } else {
                contentBuilder.append(
                        "<div class='row' child-index=" + String.valueOf(childIndex) + "> " +
                                "<div class='flex'>" +
                                "<div class='key'>" + key + "</div>" +
                                "<div class='value'>" + replaceToBr(key, String.valueOf(v)) + "</div>" +
                                "</div>" +
                                "</div>"
                );
            }

        }

        return contentBuilder.toString();
    }

    private String replaceToBr(String key, String s) {
        if (s.contains("\n")) {
            s = s.replaceAll("\n\t?", "<\\br>");
        }
        return s;
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
