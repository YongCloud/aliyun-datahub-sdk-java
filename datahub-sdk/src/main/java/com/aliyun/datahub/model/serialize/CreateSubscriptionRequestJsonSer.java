package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.common.transport.DefaultRequest;
import com.aliyun.datahub.common.transport.HttpMethod;
import com.aliyun.datahub.exception.DatahubClientException;
import com.aliyun.datahub.model.CreateSubscriptionRequest;

public class CreateSubscriptionRequestJsonSer implements Serializer<DefaultRequest, CreateSubscriptionRequest> {
    @Override
    public DefaultRequest serialize(CreateSubscriptionRequest request) throws DatahubClientException {
        DefaultRequest req = new DefaultRequest();
        req.setHttpMethod(HttpMethod.POST);
        req.setResource("/projects/" + request.getProjectName() + "/subscriptions");
        req.setBody("{\"Action\": \"create\"," +
                     "\"TopicName\":\"" + request.getTopicName() + "\"," +
                     "\"Comment\": \"" + request.getComment().replaceAll("\r\n|\r|\n", " ") + "\"}");
        return req;
    }

    private CreateSubscriptionRequestJsonSer() {}

    private static CreateSubscriptionRequestJsonSer instance;

    public static CreateSubscriptionRequestJsonSer getInstance() {
        if (instance == null) {
            instance = new CreateSubscriptionRequestJsonSer();
        }
        return instance;
    }
}
