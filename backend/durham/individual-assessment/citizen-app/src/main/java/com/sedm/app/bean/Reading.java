package com.sedm.app.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class Reading {

    private String id;
    private String userId;
    private String userName;
    private String providerName;
    private int providerId;
    private Long powerConsumption;
    private int meterId;
    private String fromDate;
    private String toDate;
    private String readingDate;
    private boolean isManual;


}
