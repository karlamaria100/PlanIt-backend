package com.fmi.planit.mapping;

import com.fmi.planit.model.Project;
import com.sun.mail.imap.protocol.BODY;

import java.io.Serializable;

public class ProjectMapping  implements Serializable {

    private Project project;

    private Boolean isAdmin;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
