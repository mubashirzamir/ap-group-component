package com.sedm.app.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@Data
public class PowerConsumptionEP {
    @Id
    private String id;
    private Long powerConsumption;
    private String fromDate;
    private String toDate;
    private boolean isManual;
    private String readingDate;
}
