package com.example.xfocus.HomePages.KategoriRecycleNav;

public class MenuRecycler {
    String name;
    private int image;

    public MenuRecycler(String name,int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public int getImage() {
        return image;
    }
}
