package com.sht.apimodels;

import com.google.gson.annotations.SerializedName;

public class CvFile {
    @SerializedName("tsync_id")
    private String tsyncId;
    @SerializedName("id")
    private int id;

    public String getTsyncId() {
        return tsyncId;
    }

    public void setTsyncId(String tsyncId) {
        this.tsyncId = tsyncId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
