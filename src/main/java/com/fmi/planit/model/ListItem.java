package com.fmi.planit.model;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;

@Entity(name = "list_items")
@Table(name = "list_items")
public class ListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "checked")
    private Boolean checked;

    @Column(name = "list_index")
    private Long listIndex;

    @ManyToOne(cascade = CascadeType.DETACH,targetEntity = ListTable.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_list")
    private ListTable listTable;

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

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getListIndex() {
        return listIndex;
    }

    public void setListIndex(Long listIndex) {
        this.listIndex = listIndex;
    }

    public ListTable getListTable() {
        return listTable;
    }

    public void setListTable(ListTable listTable) {
        this.listTable = listTable;
    }
}
