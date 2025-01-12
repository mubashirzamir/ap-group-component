package com.sdem.app.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class User {

    private String userId;
    private String name;
    private String emailId;
    private int age;
    private String city;
    private int meterId;
    private String passwd;
    private String cpasswd;
    private String mobile;


}
