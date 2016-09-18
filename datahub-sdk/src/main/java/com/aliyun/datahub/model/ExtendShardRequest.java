package com.aliyun.datahub.model;

public class ExtendShardRequest {
    private String projectName;
    private String topicName;
    private int shardNumber;

    public ExtendShardRequest(String projectName, String topicName, int shardNumber) {
        this.projectName = projectName;
        this.topicName = topicName;
        this.shardNumber = shardNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTopicName() {
        return topicName;
    }

    public int getShardNumber() {
        return shardNumber;
    }
}
