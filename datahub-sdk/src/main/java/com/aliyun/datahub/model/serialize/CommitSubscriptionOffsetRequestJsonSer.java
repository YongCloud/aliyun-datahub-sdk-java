package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.common.transport.DefaultRequest;
import com.aliyun.datahub.common.transport.HttpMethod;
import com.aliyun.datahub.common.util.JacksonParser;
import com.aliyun.datahub.exception.DatahubClientException;
import com.aliyun.datahub.model.CommitSubscriptionOffsetRequest;
import com.aliyun.datahub.model.Offset;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;
import java.util.Map;

public class CommitSubscriptionOffsetRequestJsonSer implements Serializer<DefaultRequest, CommitSubscriptionOffsetRequest> {
    @Override
    public DefaultRequest serialize(CommitSubscriptionOffsetRequest request) throws DatahubClientException {
        DefaultRequest req = new DefaultRequest();
        req.setResource("/projects/" + request.getProjectName() + "/subscriptions/" + request.getSubId() + "/offsets");
        req.setHttpMethod(HttpMethod.PUT);

        ObjectMapper mapper = JacksonParser.getObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        ObjectNode offsets = node.putObject("Offsets");
        for (Map.Entry<String, Offset> offset: request.getOffsets().entrySet()) {
            ObjectNode offsetNode = offsets.putObject(offset.getKey());
            offsetNode.put("Sequence", offset.getValue().getSequence());
            offsetNode.put("Timestamp", offset.getValue().getTimestamp());
        }

        try {
            req.setBody(mapper.writeValueAsString(node));
        } catch (IOException e) {
            throw new DatahubClientException("serialize error", e);
        }

        return req;
    }

    private CommitSubscriptionOffsetRequestJsonSer() {}

    private static CommitSubscriptionOffsetRequestJsonSer instance;

    public static CommitSubscriptionOffsetRequestJsonSer getInstance() {
        if (instance == null) {
            instance = new CommitSubscriptionOffsetRequestJsonSer();
        }
        return instance;
    }
}
