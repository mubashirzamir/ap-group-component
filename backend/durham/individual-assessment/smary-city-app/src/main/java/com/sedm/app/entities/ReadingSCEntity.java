package com.sedm.app.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Data
@Document
public class ReadingSCEntity {

    @Id
    private String id;
    private String userId;
    private String userName;
    private String providerName;
    private int providerId;

    private int meterId;
    private List<PowerConsumptionSC> powerConsumptions;
}
