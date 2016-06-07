package ua.taxi.model;


import java.io.Serializable;

/**
 * Created by serhii on 23.04.16.
 */
public abstract class User implements Serializable {

    private String phone;
    private String pass;
    private String name;

    public User(String phone, String pass, String name) {
        this.phone = phone;
        this.pass = pass;
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", pass='" + pass + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
