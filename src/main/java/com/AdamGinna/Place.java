package com.AdamGinna;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "Places" )
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
