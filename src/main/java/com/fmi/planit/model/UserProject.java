package com.fmi.planit.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "users_projects")
@Table(name = "users_projects")
public class UserProject implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private Long userId;

    @Column(name="id_project")
    private Long projectId;

    @Column(name = "is_admin")
    private Boolean admin;


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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
