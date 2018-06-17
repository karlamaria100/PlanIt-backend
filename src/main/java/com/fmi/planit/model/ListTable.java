package com.fmi.planit.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "lists")
@Table(name = "lists")
public class ListTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.DETACH,targetEntity = Project.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_project")
    private Project project;

    @OneToMany(mappedBy = "listTable", cascade = CascadeType.ALL)
    private List<ListItem> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<ListItem> getItems() {
        return items;
    }

    public void setItems(List<ListItem> items) {
        this.items = items;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
