package com.sedm.app.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Data
@Document
public class AggregationEntity {

    @Id
    private String id;
    private Integer totalCount;
    private Long totalConsumption;
    private Integer totalReadings;
    private Integer totalActiveUser;
    private Integer providerId;
    private String providerName;
    private String userId;
}
