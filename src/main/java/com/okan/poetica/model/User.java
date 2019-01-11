package com.okan.poetica.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    private String id;

    @NotBlank(message = "Emalini buraya girebilir misin acaba?")
    @Email(message = "Aaa bu geçerli bir email değel sanırım :(")
    @Column(unique = true)
    private String email;



    @NotBlank(message = "İsmini buraya girebilir misin acaba?")
    @Size(min = 3,max = 25,message = "Bence daha güzel bir ismin var")
    private String name;

    @NotBlank(message = "Parolanı buraya girebilir misin acaba?")
    @Size(min = 6,max = 25,message = "Bence daha güzel bir parola bulabilirsin")
    private String password;

    @Pattern(regexp = "[\\d]{2}", message = "Yaşını yanlış girmiş olabilir misin?")
    private String age;

    @Pattern(regexp = "[\\d]{10}", message = "Telefon numaranı yanlış girmiş olabilir misin?")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "writer")
    private List<Content> ownContentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "writer")
    private List<Content> currentPageContentList;

    public User() {
    }

    public User(String id, String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
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

    public List<Content> getOwnContentList() {
        return ownContentList;
    }

    public void setOwnContentList(List<Content> ownContentList) {
        this.ownContentList = ownContentList;
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
                Objects.equals(email, user.email) &&
                Objects.equals(ownContentList, user.ownContentList) &&
                Objects.equals(currentPageContentList, user.currentPageContentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, email, age, ownContentList, currentPageContentList);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", ownContentList=" + ownContentList +
                ", currentPageContentList=" + currentPageContentList +
                '}';
    }
}
