package com.AdamGinna;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Blob;

@Entity
public class Place {

    @Id
    @GeneratedValue
    private int id;
    private String Name;
    private double longitude;
    private double latitude;
    private Blob image;
    private String Description;

}
