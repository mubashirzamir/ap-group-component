package com.sdem.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingInfo {

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
