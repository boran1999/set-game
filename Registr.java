package com.example.cards;

import com.google.gson.annotations.SerializedName;

public class Registr {
    @SerializedName("status")
    public String status;
    @SerializedName("token")
    public int token;

    @Override
    public String toString() {
        return "status: " + status + " token: " + token;
    }
}
