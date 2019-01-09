package com.macfu.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Role implements Serializable {

    @Id
    private String rid;
    private String title;
}

