package com.sparta.projectapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "itemtypes")
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private Integer id;

    @Column(name = "type_name", length = 128)
    private String typeName;

    @Column(name = "type_description", length = 128)
    private String typeDescription;

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}