package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.common.transport.DefaultRequest;
import com.aliyun.datahub.common.transport.Response;
import com.aliyun.datahub.exception.DatahubClientException;
import com.aliyun.datahub.model.*;

public class JsonSerializerFactory implements SerializerFactory {
    @Override
    public ErrorParser getErrorParser() throws DatahubClientException {
        return null;
    }

    @Override
    public Serializer<DefaultRequest, CreateProjectRequest> getCreateProjectRequestSer() {
        return CreateProjectRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<CreateProjectResult, CreateProjectRequest, Response> getCreateProjectResultDeser() {
        return CreateProjectResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, DeleteProjectRequest> getDeleteProjectRequestSer() {
        return DeleteProjectRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<DeleteProjectResult, DeleteProjectRequest, Response> getDeleteProjectResultDeser() {
        return DeleteProjectResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, GetProjectRequest> getGetProjectRequestSer() {
        return GetProjectRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<GetProjectResult, GetProjectRequest, Response> getGetProjectResultDeser() {
        return GetProjectResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, ListProjectRequest> getListProjectRequestSer() {
        return ListProjectRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<ListProjectResult, ListProjectRequest, Response> getListProjectResultDeser() {
        return ListProjectResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, CreateTopicRequest> getCreateTopicRequestSer() {
        return CreateTopicRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<CreateTopicResult, CreateTopicRequest, Response> getCreateTopicResultDeser() {
        return CreateTopicResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, DeleteTopicRequest> getDeleteTopicRequestSer() {
        return DeleteTopicRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<DeleteTopicResult, DeleteTopicRequest, Response> getDeleteTopicResultDeser() {
        return DeleteTopicResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, GetTopicRequest> getGetTopicRequestSer() {
        return GetTopicRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<GetTopicResult, GetTopicRequest, Response> getGetTopicResultDeser() {
        return GetTopicResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, ListTopicRequest> getListTopicRequestSer() {
        return ListTopicRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<ListTopicResult, ListTopicRequest, Response> getListTopicResultDeser() {
        return ListTopicResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, UpdateTopicRequest> getUpdateTopicRequestSer() {
        return UpdateTopicRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<UpdateTopicResult, UpdateTopicRequest, Response> getUpdateTopicResultDeser() {
        return UpdateTopicResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, ListShardRequest> getListShardRequestSer() {
        return ListShardRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<ListShardResult, ListShardRequest, Response> getListShardResultDeser() {
        return ListShardResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, SplitShardRequest> getSplitShardRequestSer() {
        return SplitShardRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<SplitShardResult, SplitShardRequest, Response> getSplitShardResultDeser() {
        return SplitShardResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, MergeShardRequest> getMergeShardRequestSer() {
        return MergeShardRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<MergeShardResult, MergeShardRequest, Response> getMergeShardResultDeser() {
        return MergeShardResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, GetCursorRequest> getGetCursorRequestSer() {
        return GetCursorRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<GetCursorResult, GetCursorRequest, Response> getGetCursorResultDeser() {
        return GetCursorResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, GetRecordsRequest> getGetRecordsRequestSer() {
        return GetRecordsRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<GetRecordsResult, GetRecordsRequest, Response> getGetRecordsResultDeser() {
        return GetRecordsResultJsonDeser.getInstance();
    }

    @Override
    public Serializer<DefaultRequest, PutRecordsRequest> getPutRecordsRequestSer() {
        return PutRecordsRequestJsonSer.getInstance();
    }

    @Override
    public Deserializer<PutRecordsResult, PutRecordsRequest, Response> getPutRecordsResultDeser() {
        return PutRecordsResultJsonDeser.getInstance();
    }

    private JsonSerializerFactory() {
    }

    private static JsonSerializerFactory instance;

    public static JsonSerializerFactory getInstance() {
        if (instance == null) {
            instance = new JsonSerializerFactory();
        }
        return instance;
    }
}
