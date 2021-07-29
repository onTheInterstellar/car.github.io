package com.ruoyi.business.domain;

import java.util.Date;

public class FollowInfo extends PotentialCustomer{



    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    private Long followId;
    private String customerId;
    private String chatInformation;
    private Date followUpTime;

    public String getCustomerId() {

        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getChatInformation() {
        return chatInformation;
    }

    public void setChatInformation(String chatInformation) {
        this.chatInformation = chatInformation;
    }

    public Date getFollowUpTime() {
        return followUpTime;
    }

    public void setFollowUpTime(Date followUpTime) {
        this.followUpTime = followUpTime;
    }




}
