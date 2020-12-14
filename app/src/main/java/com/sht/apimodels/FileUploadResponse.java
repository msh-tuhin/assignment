package com.sht.apimodels;

import com.google.gson.annotations.SerializedName;

public class FileUploadResponse {

    @SerializedName("tsync_id")
    private String tsyncId;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("date_created")
    private Long dateCreated;
    @SerializedName("last_updated")
    private Long lastUpdated;
    @SerializedName("file")
    private String file;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTsyncId() {
        return tsyncId;
    }

    public void setTsyncId(String tsyncId) {
        this.tsyncId = tsyncId;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}

