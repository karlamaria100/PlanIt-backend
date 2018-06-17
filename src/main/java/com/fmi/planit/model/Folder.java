package com.fmi.planit.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "folders")
@Table(name = "folders")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<File> files;

    @ManyToOne(cascade = CascadeType.DETACH,targetEntity = Project.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_project")
    private Project project;

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

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
