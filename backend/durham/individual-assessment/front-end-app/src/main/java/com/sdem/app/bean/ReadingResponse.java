package com.sdem.app.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Data
public class ReadingResponse {

    private String id;
    private String userId;
    private String userName;
    private String providerName;
    private int providerId;
    private int meterId;
    private List<PowerConsumption> powerConsumptions;

}
