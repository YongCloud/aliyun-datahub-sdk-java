package com.aliyun.datahub.model;

import java.util.ArrayList;
import java.util.List;

public class OdpsDesc {
    private String projectName;
    private String tableName;
    private String odpsEndpoint;
    private String tunnelEndpoint;
    private String accessId;
    private String accessKey;

    public OdpsDesc() {}

    public String getProject() {
        return projectName;
    }

    public void setProject(String projectName) {
        this.projectName = projectName;
    }

    public String getTable() {
        return tableName;
    }

    public void setTable(String tableName) {
        this.tableName = tableName;
    }

    public String getOdpsEndpoint() {
        return odpsEndpoint;
    }

    public void setOdpsEndpoint(String odpsEndpoint) {
        this.odpsEndpoint = odpsEndpoint;
    }

    public String getTunnelEndpoint() {
        return tunnelEndpoint;
    }

    public void setTunnelEndpoint(String tunnelEndpoint) {
        this.tunnelEndpoint = tunnelEndpoint;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
