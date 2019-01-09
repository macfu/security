package com.macfu.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Member implements Serializable {

    @Id
    private String id;
    private String name;
    private String password;
    private String enabled;
}
