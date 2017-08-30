package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.common.transport.Response;
import com.aliyun.datahub.common.util.JacksonParser;
import com.aliyun.datahub.exception.DatahubServiceException;
import com.aliyun.datahub.model.GetSubscriptionOffsetResult;
import com.aliyun.datahub.model.GetSubscriptionOffsetRequest;
import com.aliyun.datahub.model.Offset;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.*;

public class GetSubscriptionOffsetResultJsonDeser implements Deserializer<GetSubscriptionOffsetResult, GetSubscriptionOffsetRequest, Response> {
    @Override
    public GetSubscriptionOffsetResult deserialize(GetSubscriptionOffsetRequest request, Response response) throws DatahubServiceException {
        if (!response.isOK()) {
            throw JsonErrorParser.getInstance().parse(response);
        }

        GetSubscriptionOffsetResult rs = new GetSubscriptionOffsetResult();
        ObjectMapper mapper = JacksonParser.getObjectMapper();

        JsonNode tree = null;
        try {
            tree = mapper.readTree(response.getBody());
        } catch (IOException e) {
            throw new DatahubServiceException(
                    "JsonParseError", "Parse body failed:" + response.getBody(), response);
        }

        JsonNode offsetsNode = tree.get("Offsets");
        if (!offsetsNode.isObject()) {
            throw new DatahubServiceException("JsonParseError", "invalid offsets node value", response);
        }

        Map<String, Offset> offsets = new HashMap<String, Offset>();
        Iterator<Map.Entry<String, JsonNode>> iterator = offsetsNode.getFields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> offsetNode = iterator.next();
            Offset offset = new Offset(offsetNode.getValue().get("Sequence").asLong(), offsetNode.getValue().get("Timestamp").asLong());
            offsets.put(offsetNode.getKey(), offset);
        }

        rs.setOffsets(offsets);

        return rs;
    }

    private GetSubscriptionOffsetResultJsonDeser() {}

    private static GetSubscriptionOffsetResultJsonDeser instance;

    public static GetSubscriptionOffsetResultJsonDeser getInstance() {
        if (instance == null) {
            instance = new GetSubscriptionOffsetResultJsonDeser();
        }
        return instance;
    }
}
