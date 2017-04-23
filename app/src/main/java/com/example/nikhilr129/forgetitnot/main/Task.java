package com.example.nikhilr129.forgetitnot.main;

import io.realm.RealmModel;

/**
 * Created by kanchicoder on 4/10/17.
 */

public class Task implements RealmModel {
    private String title, event;
    private long id;
    private int thumbnail;
    public Task(String title,long id,String event, int thumbnail) {
        this.title = title;
        this.event = event;
        this.id=id;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
