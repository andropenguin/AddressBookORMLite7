package com.sarltokyo.addressbookormlite7.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by osabe on 15/07/17.
 */
@DatabaseTable(tableName = "address")
public class Address {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String zipcode;
    @DatabaseField
    private String prefecture;
    @DatabaseField
    private String city;
    @DatabaseField
    private String other;


    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
