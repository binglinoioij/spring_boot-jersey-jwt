package com.example.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by binglin on 2016/9/16.
 */
@Document
public class Sequence implements Serializable {

    private static final long serialVersionUID = -6025274941175662749L;

    @Id
    private String collName;

    private long value = 20000;

    public Sequence() {
    }

    public Sequence(String collName, long value) {
        this.collName = collName;
        this.value = value;
    }


    public Sequence(String collName) {
        this.collName = collName;
    }

    public String getCollName() {
        return collName;
    }

    public void setCollName(String collName) {
        this.collName = collName;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}