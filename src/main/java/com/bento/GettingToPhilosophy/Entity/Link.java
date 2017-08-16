package com.bento.GettingToPhilosophy.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Link {

    @Id
    @GeneratedValue
    @Column(name = "link_id")
    private int id;
    @Column
    private String title;
    @Column
    private String URL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
