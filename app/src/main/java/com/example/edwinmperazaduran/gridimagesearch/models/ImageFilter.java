package com.example.edwinmperazaduran.gridimagesearch.models;

import java.io.Serializable;

public class ImageFilter implements Serializable {
    private String size;
    private String color;
    private String type;
    private String site;

    public ImageFilter(){
        this.size = "Any";
        this.color = "Any";
        this.type = "Any";
        this.site = "";
    }

    public ImageFilter(String size, String color, String type, String site) {
        this.size = size;
        this.color = color;
        this.type = type;
        this.site = site;
    }

    public boolean noSelections(){
        if (size.equals("Any") && color.equals("Any") && type.equals("Any") && site.equals("")) {
            return true;
        }
        return false;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }
}
