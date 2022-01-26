package com.sparta.projectapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "itemtypes")
public class Itemtype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}