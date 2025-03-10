package com.example.tiendaecci;

import com.google.gson.annotations.SerializedName;

public class ServerIpResponse {
    @SerializedName("ip")
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
