package com.axmor.Models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Issue {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String name;
    private String description;
    private Status status;
    private String date;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Issue() {
    }

    public Issue(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.Created;
        this.date = new SimpleDateFormat().format(new Date());
        this.comments = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", date='" + date + '\'' +
                ", comments=" + comments +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return id == issue.id &&
                Objects.equals(name, issue.name) &&
                Objects.equals(description, issue.description) &&
                status == issue.status &&
                Objects.equals(date, issue.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, date);
    }
}
