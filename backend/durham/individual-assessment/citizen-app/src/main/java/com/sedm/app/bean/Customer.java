package com.sedm.app.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class Customer {
    private String userId;
    private String name;
    private String emailId;
    private int age;
    private String city = "Sunderland";
    private int meterId;
    private String passwd;
    private String mobile;

}
