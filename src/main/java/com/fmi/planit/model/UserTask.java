package com.fmi.planit.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "users_tasks")
@Table(name = "users_tasks")
public class UserTask implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private Long userId;

    @Column(name="id_task")
    private Long taskId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
