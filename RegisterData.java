package com.example.pc.card_set;

import com.google.gson.annotations.SerializedName;

public class RegisterData {
    @SerializedName("status")
    public String status;
    @SerializedName("token")
    public String token;

    @Override
    public String toString() {
        return "status: " + status + " token: " + token;
    }
}
