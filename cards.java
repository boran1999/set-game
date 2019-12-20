package com.example.cards;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class cards {
    @SerializedName("count")
    public int count;
    @SerializedName("color")
    public int color;
    @SerializedName("shape")
    public int shape;
    @SerializedName("fill")
    public int fill;

    @Override
    public String toString() {
        return "{count: " + count + "color: " + color + "shape: " + shape + "fill: " + fill+"}";
    }
}
