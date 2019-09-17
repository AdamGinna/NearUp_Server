package com.AdamGinna;

import javax.persistence.*;
import java.security.acl.Owner;
import java.sql.Blob;

@Entity
@Table(name = "Places" )
public class Place{

    @Id
    private int id;
    private String Name;
    private int owner;
    private double longitude;
    private double latitude;
    private Blob image;
    private String Description;




    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", image=" + image +
                ", Description='" + Description + '\'' +
                '}';
    }

    public int getOwner() {
        return owner;
    }

    public void setUser(int user) {
        owner = user;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public static int compare(Place p1,Place p2,double x,double y)
    {
        return (int)(Math.pow(p1.latitude-x,2)-Math.pow(p1.longitude-y,2)) - (int)(Math.pow(p2.latitude-x,2)-Math.pow(p2.longitude-y,2));
    }

}
