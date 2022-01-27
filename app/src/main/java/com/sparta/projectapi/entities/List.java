package com.sparta.projectapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "lists", indexes = {
        @Index(name = "belongs_to_user", columnList = "belongs_to_user")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id", nullable = false)
    private Integer id;

    @Column(name = "list_title", nullable = false)
    private String listTitle;

    @Column(name = "list_description", nullable = false)
    private String listDescription;

    @ManyToOne(optional = false)
    @JoinColumn(name = "belongs_to_user", nullable = false)
    private User belongsToUser;

    public List(String listTitle, String listDescription, User belongsToUser) {
        this.listTitle = listTitle;
        this.listDescription = listDescription;
        this.belongsToUser = belongsToUser;
    }

    public List() {
    }

    public User getBelongsToUser() {
        return belongsToUser;
    }

    public void setBelongsToUser(User belongsToUser) {
        this.belongsToUser = belongsToUser;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "List{" +
                "id=" + id +
                ", listTitle='" + listTitle + '\'' +
                ", listDescription='" + listDescription + '\'' +
                ", belongsToUser=" + belongsToUser +
                '}';
    }
}