package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.common.transport.Response;
import com.aliyun.datahub.exception.DatahubServiceException;
import com.aliyun.datahub.model.UpdateConnectorStateRequest;
import com.aliyun.datahub.model.UpdateConnectorStateResult;


public class UpdateConnectorStateResultJsonDeser implements Deserializer<UpdateConnectorStateResult,UpdateConnectorStateRequest,Response> {
    @Override
    public UpdateConnectorStateResult deserialize(UpdateConnectorStateRequest request, Response response) throws DatahubServiceException {
        if (!response.isOK()) {
            throw JsonErrorParser.getInstance().parse(response);
        }
        return new UpdateConnectorStateResult();
    }

    private UpdateConnectorStateResultJsonDeser() {

    }

    private static UpdateConnectorStateResultJsonDeser instance;

    public static UpdateConnectorStateResultJsonDeser getInstance() {
        if (instance == null)
            instance = new UpdateConnectorStateResultJsonDeser();
        return instance;
    }
}
