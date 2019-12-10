package com.example.entity;

public class AddNav {
    private long id;
    private int positionId;
    private String titleName;
    private String picture;
    private int linkType;
    private long linkTarget;
    private String status;
    private String createdAt;
    private  String updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitle(String titleName) {
        this.titleName = titleName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public long getLinkTarget() {
        return linkTarget;
    }

    public void setLinkTarget(long linkTarget) {
        this.linkTarget = linkTarget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "AddNav{" +
                "id=" + id +
                ", positionId=" + positionId +
                ", titleName='" + titleName + '\'' +
                ", picture='" + picture + '\'' +
                ", linkType=" + linkType +
                ", linkTarget=" + linkTarget +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
