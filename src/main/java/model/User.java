package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String name;
    private String surName;
    private String email;
    private String password;

    public User(String name, String surName, String email, String password) {
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.password = password;
    }
}
