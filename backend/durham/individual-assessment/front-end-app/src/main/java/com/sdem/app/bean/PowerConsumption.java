package com.sdem.app.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Data
public class PowerConsumption {
    private String id;
    private Long powerConsumption;
    private String fromDate;
    private String toDate;
    private boolean isManual;
    private String readingDate;
}
