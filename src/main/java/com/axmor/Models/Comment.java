package com.axmor.Models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;
    private Status status;
    private String text;
    private String date;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Comment() {
    }

    public Comment(Status status, String text) {
        this.status = status;
        this.text = text;
        date = new SimpleDateFormat().format(new Date());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", status=" + status +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
