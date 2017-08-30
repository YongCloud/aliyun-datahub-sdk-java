package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.common.transport.Response;
import com.aliyun.datahub.exception.DatahubServiceException;
import com.aliyun.datahub.model.CommitSubscriptionOffsetRequest;
import com.aliyun.datahub.model.UpdateSubscriptionOffsetResult;

public class CommitSubscriptionOffsetResultJsonDeser implements Deserializer<UpdateSubscriptionOffsetResult, CommitSubscriptionOffsetRequest, Response> {
    @Override
    public UpdateSubscriptionOffsetResult deserialize(CommitSubscriptionOffsetRequest request, Response response) throws DatahubServiceException {
        if (!response.isOK()) {
            throw JsonErrorParser.getInstance().parse(response);
        }
        return new UpdateSubscriptionOffsetResult();
    }

    private CommitSubscriptionOffsetResultJsonDeser() {}

    private static CommitSubscriptionOffsetResultJsonDeser instance;

    public static CommitSubscriptionOffsetResultJsonDeser getInstance() {
        if (instance == null) {
            instance = new CommitSubscriptionOffsetResultJsonDeser();
        }
        return instance;
    }
}
