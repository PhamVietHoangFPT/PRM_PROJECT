package com.datj.mobile.data.remote.model;

import java.util.List;

public class Product {
    private int accessoryId;
    private String name;
    private int karat;
    private double materialWeight;
    private int quantity;
    private AccessoryType accessoryType;
    private Shape shape;

    private List<AccessoryImage> accessoryImages;

    // Nested classes for AccessoryType, Shape, and AccessoryImage
    public static class AccessoryType {
        private int accessoryTypeId;
        private String name;
        private double processingPrice;

        public int getAccessoryTypeId() { return accessoryTypeId; }
        public void setAccessoryTypeId(int accessoryTypeId) { this.accessoryTypeId = accessoryTypeId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getProcessingPrice() { return processingPrice; }
        public void setProcessingPrice(double processingPrice) { this.processingPrice = processingPrice; }
    }

    public static class Shape {
        private int shapeId;
        private String name;

        public int getShapeId() { return shapeId; }
        public void setShapeId(int shapeId) { this.shapeId = shapeId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class AccessoryImage {
        private int accessoryImageId;
        private String url;

        public int getAccessoryImageId() { return accessoryImageId; }
        public void setAccessoryImageId(int accessoryImageId) { this.accessoryImageId = accessoryImageId; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    // Getters and Setters
    public int getAccessoryId() { return accessoryId; }
    public void setAccessoryId(int accessoryId) { this.accessoryId = accessoryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getKarat() { return karat; }
    public void setKarat(int karat) { this.karat = karat; }
    public double getMaterialWeight() { return materialWeight; }
    public void setMaterialWeight(double materialWeight) { this.materialWeight = materialWeight; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public AccessoryType getAccessoryType() { return accessoryType; }
    public void setAccessoryType(AccessoryType accessoryType) { this.accessoryType = accessoryType; }
    public Shape getShape() { return shape; }
    public void setShape(Shape shape) { this.shape = shape; }
    public List<AccessoryImage> getAccessoryImages() { return accessoryImages; }
    public void setAccessoryImages(List<AccessoryImage> accessoryImages) { this.accessoryImages = accessoryImages; }
}