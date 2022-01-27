package com.sparta.projectapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "items", indexes = {
        @Index(name = "user_id", columnList = "user_id"),
        @Index(name = "item_type", columnList = "item_type")
})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Integer id;

    @Column(name = "item_name", length = 128)
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "item_type")
    private ItemType itemType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item(String itemName, ItemType itemType, User user) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.user = user;
    }

    public Item() {
    }
}