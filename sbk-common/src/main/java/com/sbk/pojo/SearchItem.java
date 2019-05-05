package com.sbk.pojo;

import java.io.Serializable;

/**
 * @author You
 * @create 2018-11-02 16:12
 */
public class SearchItem implements Serializable {

    private String id;
    private String title;
    private String sell_point;
    private Long price;
    private String image;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public void setSellpoint(String sell_point) {
        this.sell_point = sell_point;
    }


    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
