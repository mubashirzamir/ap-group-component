package com.sedm.app.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ResponseEP {

    private String message;
    private String status;
    private String id;
}
