package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.common.transport.DefaultRequest;
import com.aliyun.datahub.common.transport.HttpMethod;
import com.aliyun.datahub.exception.DatahubClientException;
import com.aliyun.datahub.model.GetSubscriptionOffsetRequest;

public class GetSubscriptionOffsetRequestJsonSer implements Serializer<DefaultRequest, GetSubscriptionOffsetRequest> {
    @Override
    public DefaultRequest serialize(GetSubscriptionOffsetRequest request) throws DatahubClientException {
        DefaultRequest req = new DefaultRequest();
        req.setResource("/projects/" + request.getProjectName() + "/subscriptions/" + request.getSubId() + "/offsets");
        req.setHttpMethod(HttpMethod.GET);
        return req;
    }

    private GetSubscriptionOffsetRequestJsonSer() {}

    private static GetSubscriptionOffsetRequestJsonSer instance;

    public static GetSubscriptionOffsetRequestJsonSer getInstance() {
        if (instance == null) {
            instance = new GetSubscriptionOffsetRequestJsonSer();
        }
        return instance;
    }
}
