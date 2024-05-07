package Clases;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    private int id;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("role")
    private String role;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public Usuario() {}

    public Usuario(String name, String role, String email, String password) {
        this.fullname = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public Usuario(int id, String name, String role, String email, String password) {
        this.id = id;
        this.fullname = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return fullname;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.fullname = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
