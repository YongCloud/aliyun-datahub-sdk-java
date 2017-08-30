package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.common.transport.DefaultRequest;
import com.aliyun.datahub.common.transport.HttpMethod;
import com.aliyun.datahub.exception.DatahubClientException;
import com.aliyun.datahub.model.UpdateSubscriptionRequest;

public class UpdateSubscriptionRequestJsonSer implements Serializer<DefaultRequest, UpdateSubscriptionRequest> {
    @Override
    public DefaultRequest serialize(UpdateSubscriptionRequest request) throws DatahubClientException {
        DefaultRequest req = new DefaultRequest();
        req.setHttpMethod(HttpMethod.PUT);
        req.setResource("/projects/" + request.getProjectName() + "/subscriptions/" + request.getSubId());
        req.setBody("{\"Comment\": \"" + request.getComment() + "\"}");
        return req;
    }

    private UpdateSubscriptionRequestJsonSer() {}

    private static UpdateSubscriptionRequestJsonSer instance;

    public static UpdateSubscriptionRequestJsonSer getInstance() {
        if (instance == null) {
            instance = new UpdateSubscriptionRequestJsonSer();
        }
        return instance;
    }
}
