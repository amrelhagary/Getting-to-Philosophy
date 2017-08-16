package com.bento.GettingToPhilosophy.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class Wiki implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wiki_id")
    private int id;
    @Column
    private String title;
    @Column
    private String URL;
    @Column
    private int linkCount;

    @OneToMany(targetEntity = Link.class, cascade = {CascadeType.ALL})
    private List<Link> links;

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

    public int getLinkCount() {
        return linkCount;
    }

    public void setLinkCount(int linkCount) {
        this.linkCount = linkCount;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public String toString(){
        return this.getTitle() + " ->" + this.getURL() + " : " + this.getLinkCount();
    }
}
