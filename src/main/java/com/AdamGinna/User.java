package com.AdamGinna;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String nick;
    private String password;
    private String email;

}
