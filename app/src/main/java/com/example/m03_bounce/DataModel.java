package com.example.m03_bounce;

public class DataModel {
    private long id;
    float x;  // Ball's center (x,y)
    float y;
    float dx; // Ball's speed
    float dy;

    public DataModel() {
        this(0, 0.0f, 0.0f, 0.0f, 0.0f); // Default constructor
    }

    // Data Layout
    public DataModel(long id, Float x, Float y, Float dx, Float dy) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    // Data String
    @Override
    public String toString() {
        return "DataModel{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dx=" + dx +
                ", dy=" + dy +
                '}';
    }

    // Set Model Value
    public void setModelPosVel(Float x, Float y, Float dx, Float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public float getModelX() {
        return this.x;
    }

    public float getModelY() {
        return this.y;
    }

    public float getModelDX() {
        return this.dx;
    }

    public float getModelDY() {
        return this.dy;
    }
}
