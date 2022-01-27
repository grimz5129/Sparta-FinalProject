package com.sparta.projectapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "listrows", indexes = {
        @Index(name = "list_id", columnList = "list_id"),
        @Index(name = "item_id", columnList = "item_id")
})
public class ListRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    private List list;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ListRow(List list, Item item) {
        this.list = list;
        this.item = item;
    }

    public ListRow() {
    }
}