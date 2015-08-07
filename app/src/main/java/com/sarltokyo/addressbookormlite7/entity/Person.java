package com.sarltokyo.addressbookormlite7.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by osabe on 15/07/17.
 */
@DatabaseTable(tableName = "person")
public class Person {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(unique = true)
    private String name;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Address address;

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
