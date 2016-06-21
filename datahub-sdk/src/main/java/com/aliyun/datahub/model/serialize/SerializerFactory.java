package com.aliyun.datahub.model.serialize;

import com.aliyun.datahub.common.transport.DefaultRequest;
import com.aliyun.datahub.common.transport.Response;
import com.aliyun.datahub.exception.DatahubClientException;
import com.aliyun.datahub.model.*;

public interface SerializerFactory {
    public ErrorParser getErrorParser() throws DatahubClientException;

    public Serializer<DefaultRequest, CreateProjectRequest> getCreateProjectRequestSer() throws DatahubClientException;

    public Deserializer<CreateProjectResult, CreateProjectRequest, Response> getCreateProjectResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, DeleteProjectRequest> getDeleteProjectRequestSer() throws DatahubClientException;

    public Deserializer<DeleteProjectResult, DeleteProjectRequest, Response> getDeleteProjectResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, GetProjectRequest> getGetProjectRequestSer() throws DatahubClientException;

    public Deserializer<GetProjectResult, GetProjectRequest, Response> getGetProjectResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, ListProjectRequest> getListProjectRequestSer() throws DatahubClientException;

    public Deserializer<ListProjectResult, ListProjectRequest, Response> getListProjectResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, CreateTopicRequest> getCreateTopicRequestSer() throws DatahubClientException;

    public Deserializer<CreateTopicResult, CreateTopicRequest, Response> getCreateTopicResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, DeleteTopicRequest> getDeleteTopicRequestSer() throws DatahubClientException;

    public Deserializer<DeleteTopicResult, DeleteTopicRequest, Response> getDeleteTopicResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, GetTopicRequest> getGetTopicRequestSer() throws DatahubClientException;

    public Deserializer<GetTopicResult, GetTopicRequest, Response> getGetTopicResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, ListTopicRequest> getListTopicRequestSer() throws DatahubClientException;

    public Deserializer<ListTopicResult, ListTopicRequest, Response> getListTopicResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, UpdateTopicRequest> getUpdateTopicRequestSer() throws DatahubClientException;

    public Deserializer<UpdateTopicResult, UpdateTopicRequest, Response> getUpdateTopicResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, ListShardRequest> getListShardRequestSer() throws DatahubClientException;

    public Deserializer<ListShardResult, ListShardRequest, Response> getListShardResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, GetCursorRequest> getGetCursorRequestSer() throws DatahubClientException;

    public Deserializer<GetCursorResult, GetCursorRequest, Response> getGetCursorResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, GetRecordsRequest> getGetRecordsRequestSer() throws DatahubClientException;

    public Deserializer<GetRecordsResult, GetRecordsRequest, Response> getGetRecordsResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, PutRecordsRequest> getPutRecordsRequestSer() throws DatahubClientException;

    public Deserializer<PutRecordsResult, PutRecordsRequest, Response> getPutRecordsResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, SplitShardRequest> getSplitShardRequestSer() throws DatahubClientException;

    public Deserializer<SplitShardResult, SplitShardRequest, Response> getSplitShardResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, MergeShardRequest> getMergeShardRequestSer() throws DatahubClientException;

    public Deserializer<MergeShardResult, MergeShardRequest, Response> getMergeShardResultDeser() throws DatahubClientException;
}
