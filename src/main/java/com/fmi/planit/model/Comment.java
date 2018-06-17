package com.fmi.planit.model;

import com.fmi.planit.repository.UserRepository;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;

@Entity(name = "comments")
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = Task.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_task")
    private Task task;

    @ManyToOne(cascade = CascadeType.DETACH,targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "timestamp")
    private String timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
