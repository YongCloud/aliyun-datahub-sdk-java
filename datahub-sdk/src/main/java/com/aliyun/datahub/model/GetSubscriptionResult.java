package com.aliyun.datahub.model;

public class GetSubscriptionResult {
	private boolean isOwner;
	private String subId;
    private String topicName;
    private String comment;
    private long createTime;
    private long lastModifyTime;

    public boolean getIsOwner() {
    	return isOwner;
    }
    
    public void setIsOwner(boolean isOwner) {
    	this.isOwner = isOwner;
    }

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}
	
    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public void setCreateTime(long createTime) {
    	this.createTime = createTime;
    }
    
    public long getCreateTime() {
    	return createTime;
    }
    
    public void setLastModifyTime(long lastModifyTime) {
    	this.lastModifyTime = lastModifyTime;
    }
    
    public long getLastModifyTime() {
    	return lastModifyTime;
    }
}
