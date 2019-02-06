package com.okan.poetica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Content implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;
    @Column
    private ContentType contentType;

    @NotBlank
    @Column
    private String title;

    @NotBlank
    @Column
    @Size(max = 2000)
    private String content;


    @ManyToOne(fetch = FetchType.EAGER)
    /* @JoinColumn(name = "writer_content",foreignKey = @ForeignKey(name = "writer_fk")) */
    private User writer;


    @Column
    private int likeNumber;

    public Content() {
        this.likeNumber=0;
    }

    public Content(ContentType contentType, String title, String content, User writer, int likeNumber) {
        this.contentType = contentType;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.likeNumber = likeNumber;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content1 = (Content) o;
        return likeNumber == content1.likeNumber &&
                contentType == content1.contentType &&
                Objects.equals(title, content1.title) &&
                Objects.equals(content, content1.content) &&
                Objects.equals(writer, content1.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentType, title, content, writer, likeNumber);
    }

    @Override
    public String toString() {
        return "Content{" +
                "contentType=" + contentType +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer=" + writer +
                ", likeNumber=" + likeNumber +
                '}';
    }
}
