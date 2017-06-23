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

    public Serializer<DefaultRequest, ListConnectorRequest> getListConnectorRequestSer() throws DatahubClientException;

    public Deserializer<ListConnectorResult, ListConnectorRequest, Response> getListConnectorResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, CreateConnectorRequest> getCreateConnectorRequestSer() throws DatahubClientException;

    public Deserializer<CreateConnectorResult, CreateConnectorRequest, Response> getCreateConnectorResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, GetConnectorRequest> getGetConnectorRequestSer() throws DatahubClientException;

    public Deserializer<GetConnectorResult, GetConnectorRequest, Response> getGetConnectorResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, DeleteConnectorRequest> getDeleteConnectorRequestSer() throws DatahubClientException;

    public Deserializer<DeleteConnectorResult, DeleteConnectorRequest, Response> getDeleteConnectorResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, ReloadConnectorRequest> getReloadConnectorRequestSer() throws DatahubClientException;

    public Deserializer<ReloadConnectorResult, ReloadConnectorRequest, Response> getReloadConnectorResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, GetConnectorShardStatusRequest> getGetConnectorShardStatusRequestSer() throws DatahubClientException;

    public Deserializer<GetConnectorShardStatusResult, GetConnectorShardStatusRequest, Response> getGetConnectorShardStatusResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, GetBlobRecordsRequest> getGetBlobRecordsRequestSer() throws DatahubClientException;

    public Deserializer<GetBlobRecordsResult, GetBlobRecordsRequest, Response> getGetBlobRecordsResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, PutBlobRecordsRequest> getPutBlobRecordsRequestSer() throws DatahubClientException;

    public Deserializer<PutBlobRecordsResult, PutBlobRecordsRequest, Response> getPutBlobRecordsResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, ExtendShardRequest> getExtendShardRequestSer() throws DatahubClientException;

    public Deserializer<ExtendShardResult, ExtendShardRequest, Response> getExtendShardResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, AppendFieldRequest> getAppendFieldRequestSer() throws DatahubClientException;

    public Deserializer<AppendFieldResult, AppendFieldRequest, Response> getAppendFieldResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, AppendConnectorFieldRequest> getAppendConnectorFieldRequestSer() throws DatahubClientException;

    public Deserializer<AppendConnectorFieldResult, AppendConnectorFieldRequest, Response> getAppendConnectorFieldResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, UpdateConnectorStateRequest> getUpdateConnectorStateRequestSer() throws DatahubClientException;

    public Deserializer<UpdateConnectorStateResult, UpdateConnectorStateRequest, Response> getUpdateConnectorStateResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, UpdateConnectorShardContextRequest> getUpdateConnectorShardContextRequestSer() throws DatahubClientException;

    public Deserializer<UpdateConnectorShardContextResult, UpdateConnectorShardContextRequest, Response> getUpdateConnectorShardContextResultDeser() throws DatahubClientException;

    public Serializer<DefaultRequest, GetMeteringInfoRequest> getGetMeteringInfoRequestSer() throws DatahubClientException;

    public Deserializer<GetMeteringInfoResult, GetMeteringInfoRequest, Response> getGetMeteringInfoResultDeser() throws DatahubClientException;
}
