package com.example.sweater.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @NotBlank(message = "Please fill the message") // поле не должно быть пустым, и подсказка (message = "подсказка")
    @Length(max = 2048, message = "Message too long (more than 2048 symbols)") // При превышении 2048 символов в сообщении вылезет подсказка
    private String text;
    @Length(max = 255, message = "Tag too long (more than 255 symbols)") // При превышении 2048 символов в сообщении вылезет подсказка
    private String tag;
    private String filename;


    // Много сообщений - один пользователь. Many to One
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") // чтобы колонка называлась user_id вместо дефолтной в данном случае author_id
    private User author;

    public Message() {
    }

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
