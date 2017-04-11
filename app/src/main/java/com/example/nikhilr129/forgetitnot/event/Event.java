package com.example.nikhilr129.forgetitnot.event;
/**
 * Created by kanchicoder on 4/10/17.
 */

public class Event {
    private String name;
    private int thumbnail;
    private boolean isSelected;

    public void setSelected(Boolean value){
        this.isSelected = value;
    }

    public boolean isSelected(){
        return isSelected;
    }
    public Event() {
        this.isSelected = false;

    }

    public Event(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
