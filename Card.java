package com.example.cards;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Card {
    @SerializedName("status")
    public String status;
    ArrayList<cards> cards;

    @Override
    public String toString() {
        return "Status: " + status + " cards " + cards.toString();
    }
}

