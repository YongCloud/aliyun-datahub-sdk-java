package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.common.transport.Response;
import com.aliyun.datahub.exception.DatahubServiceException;
import com.aliyun.datahub.model.UpdateConnectorShardContextRequest;
import com.aliyun.datahub.model.UpdateConnectorShardContextResult;


public class UpdateConnectorShardContextResultJsonDeser implements Deserializer<UpdateConnectorShardContextResult,UpdateConnectorShardContextRequest,Response> {
    @Override
    public UpdateConnectorShardContextResult deserialize(UpdateConnectorShardContextRequest request, Response response) throws DatahubServiceException {
        if (!response.isOK()) {
            throw JsonErrorParser.getInstance().parse(response);
        }
        return new UpdateConnectorShardContextResult();
    }

    private UpdateConnectorShardContextResultJsonDeser() {

    }

    private static UpdateConnectorShardContextResultJsonDeser instance;

    public static UpdateConnectorShardContextResultJsonDeser getInstance() {
        if (instance == null)
            instance = new UpdateConnectorShardContextResultJsonDeser();
        return instance;
    }
}
