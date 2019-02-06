package com.okan.poetica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class User implements Serializable {

    @Id
    private String id;

    @NotBlank(message = "Emalini buraya girebilir misin acaba?")
    @Email(message = "Aaa bu geçerli bir email değil sanırım :(")
    @Column(unique = true)
    private String email;


    @NotBlank(message = "İsmini buraya girebilir misin acaba?")
    @Size(min = 3,max = 25,message = "Bence daha güzel bir ismin var")
    private String name;

    @NotBlank(message = "Parolanı buraya girebilir misin acaba?")
    @Size(min = 6, max = 500,message = "Bence daha güzel bir parola bulabilirsin")
    private String password;

    @Pattern(regexp = "[\\d]{2}", message = "Yaşını yanlış girmiş olabilir misin?")
    private String age;

    @Pattern(regexp = "[\\d]{10}", message = "Telefon numaranı yanlış girmiş olabilir misin?")
    private String phoneNumber;

    @OneToMany
    private List<Content> currentPageContentList;

    private String role;

    @NotBlank(message = "Kullanıcı adını buraya girebilir misin acaba?")
    @Size(min = 3,max = 50,message = "Bence daha güzel bir kullanıcı ismi bulabilirsin")
    @Column(unique = true)
    private String username;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User() {
    }

    public User(String name, String password, String email, String username) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Content> getCurrentPageContentList() {
        return currentPageContentList;
    }

    public void setCurrentPageContentList(List<Content> currentPageContentList) {
        this.currentPageContentList = currentPageContentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, email, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
