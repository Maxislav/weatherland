package com.lipatov.weatherland.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Report {

    private String id;
    private String name;
}
