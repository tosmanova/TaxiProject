package ua.taxi.model.User;


import java.io.Serializable;

/**
 * Created by serhii on 23.04.16.
 */
public abstract class User implements Serializable {

    private String phone;
    private String pass;
    private String name;

    public User(){}

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
        return " User{" +
                "phone='" + phone + '\'' +
                ", pass='" + pass + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof User)) return false;

        User user = (User) object;

        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (pass != null ? !pass.equals(user.pass) : user.pass != null) return false;
        return name != null ? name.equals(user.name) : user.name == null;

    }

    @Override
    public int hashCode() {
        int result = phone != null ? phone.hashCode() : 0;
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
