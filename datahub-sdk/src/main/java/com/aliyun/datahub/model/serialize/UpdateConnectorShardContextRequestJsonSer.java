package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.DatahubConstants;
import com.aliyun.datahub.common.transport.DefaultRequest;
import com.aliyun.datahub.common.transport.HttpMethod;
import com.aliyun.datahub.common.util.JacksonParser;
import com.aliyun.datahub.exception.DatahubClientException;
import com.aliyun.datahub.exception.InvalidParameterException;
import com.aliyun.datahub.model.UpdateConnectorShardContextRequest;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;

public class UpdateConnectorShardContextRequestJsonSer implements Serializer<DefaultRequest, UpdateConnectorShardContextRequest> {
    @Override
    public DefaultRequest serialize(UpdateConnectorShardContextRequest request) throws DatahubClientException {
        DefaultRequest req = new DefaultRequest();
        req.setResource("/projects/" + request.getProjectName()
                + "/topics/" + request.getTopicName()
                + "/connectors/" + request.getConnectorType().toString().toLowerCase()
        );
        req.setHttpMethod(HttpMethod.POST);

        ObjectMapper mapper = JacksonParser.getObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("Action", "UpdateShardContext");
        node.put(DatahubConstants.ShardId, request.getShardContext().getShardId());
        if (request.getShardContext().getCurSequence() < 0) {
            throw new InvalidParameterException("CurSequence is invalid.");
        }
        node.put(DatahubConstants.CurrentSequence, request.getShardContext().getCurSequence());
        if (request.getShardContext().getStartSequence() >= 0) {
            node.put(DatahubConstants.StartSequence, request.getShardContext().getStartSequence());
        }
        if (request.getShardContext().getEndSequence() >= 0) {
            node.put(DatahubConstants.EndSequence, request.getShardContext().getEndSequence());
        }

        try {
            req.setBody(mapper.writeValueAsString(node));
        } catch (IOException e) {
            throw new DatahubClientException("serialize error", e);
        }
        return req;
    }

    private UpdateConnectorShardContextRequestJsonSer() {

    }

    private static UpdateConnectorShardContextRequestJsonSer instance;

    public static UpdateConnectorShardContextRequestJsonSer getInstance() {
        if (instance == null)
            instance = new UpdateConnectorShardContextRequestJsonSer();
        return instance;
    }
}
