package com.aliyun.datahub.model;

import com.aliyun.datahub.exception.InvalidParameterException;

public class DeleteSubscriptionRequest {
    private String projectName;
    private String subId;

    public DeleteSubscriptionRequest(String project, String subId) {
        if (project == null) {
            throw new InvalidParameterException("project name is null");
        }

        if (subId == null) {
            throw new InvalidParameterException("sub id is null");
        }

        this.projectName = project;
        this.subId = subId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getSubId() {
        return subId;
    }
}
