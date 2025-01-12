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
public class CustomerEntity {
    @Id
    private String userId;
    private String name;
    private String emailId;
    private int age;
    private String city = "Sunderland";
    private int meterId;
    private String passwd;
    private String mobile;

}
