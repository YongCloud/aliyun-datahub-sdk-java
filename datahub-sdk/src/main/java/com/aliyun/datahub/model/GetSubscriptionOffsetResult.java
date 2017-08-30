package com.aliyun.datahub.model;

import java.util.Map;

public class GetSubscriptionOffsetResult {
    Map<String, Offset> offsets;

    public Map<String, Offset> getOffsets() {
        return offsets;
    }

    public void setOffsets(Map<String, Offset> offsets) {
        this.offsets = offsets;
    }
}
