package com.example.nikhilr129.forgetitnot.condition;
/**
 * Created by kanchicoder on 4/10/17.
 */

public class Condition {
    private String name;
    private int thumbnail;
    private boolean isSelected;

    public void setSelected(){
        this.isSelected = !this.isSelected;
    }

    public boolean isSelected(){
        return isSelected;
    }
    public Condition() {
        this.isSelected = false;

    }

    public Condition(String name, int thumbnail) {
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
