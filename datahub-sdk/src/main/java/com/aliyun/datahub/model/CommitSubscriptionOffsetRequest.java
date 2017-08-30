package com.aliyun.datahub.model;

import java.security.InvalidParameterException;
import java.util.Map;

public class CommitSubscriptionOffsetRequest {
    private String projectName;
    private String subId;
    private Map<String, Offset> offsets;

    public CommitSubscriptionOffsetRequest(String project, String subId, Map<String, Offset> offsets) {
        if (project == null) {
            throw new InvalidParameterException("project name is null");
        }

        if (subId == null) {
            throw new InvalidParameterException("sub id is null");
        }

        if (offsets == null) {
            throw new InvalidParameterException("offsets is null");
        }

        this.projectName = project;
        this.subId = subId;
        this.offsets = offsets;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getSubId() {
        return subId;
    }

    public Map<String, Offset> getOffsets() {
        return offsets;
    }
}
