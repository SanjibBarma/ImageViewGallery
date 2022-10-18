package com.example.galleryappdemo;

public class ImageModel {
    String path;
    String outletName;

    public ImageModel(String path, String outletName) {
        this.path = path;
        this.outletName = outletName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "path='" + path + '\'' +
                ", outletName='" + outletName + '\'' +
                '}';
    }
}
