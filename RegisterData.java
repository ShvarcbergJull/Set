package com.example.pc.card_set;

import com.google.gson.annotations.SerializedName;

public class RegisterData {
    @SerializedName("status")
    public String status;
    @SerializedName("token")
    public int token;

    @Override
    public String toString() {
        return "status: " + status + " token: " + String.valueOf(token);
    }
}
