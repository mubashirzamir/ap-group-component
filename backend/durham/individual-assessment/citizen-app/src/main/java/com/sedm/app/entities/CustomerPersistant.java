package com.sedm.app.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@Data

public class CustomerPersistant {
    @Id
    private int userId;
    private String name;
    private String emailId;
    private int age;
    private String city;
    private int meterId;

}
