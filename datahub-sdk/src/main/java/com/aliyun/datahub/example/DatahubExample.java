package com.aliyun.datahub.example;

import com.aliyun.datahub.DatahubClient;
import com.aliyun.datahub.DatahubConfiguration;
import com.aliyun.datahub.auth.AliyunAccount;
import com.aliyun.datahub.common.data.Field;
import com.aliyun.datahub.common.data.FieldType;
import com.aliyun.datahub.common.data.RecordSchema;
import com.aliyun.datahub.common.data.RecordType;
import com.aliyun.datahub.exception.DatahubClientException;
import com.aliyun.datahub.exception.InvalidCursorException;
import com.aliyun.datahub.model.*;

import java.util.ArrayList;
import java.util.List;

public class DatahubExample {
    private String accessId = "63wd3dpztlmb5ocdkj94pxmm";
    private String accessKey = "oRd30z7sV4hBX9aYtJgii5qnyhg=";
    private String endpoint = "http://10.101.214.153:9111";
    private String projectName = "project_test_example";
    private String topicName = "topic_test_example";
    private RecordSchema schema = null;
    private DatahubConfiguration conf;
    private DatahubClient client;

    public DatahubExample() {
        this.conf = new DatahubConfiguration(new AliyunAccount(accessId, accessKey), endpoint);
        this.client = new DatahubClient(conf);
    }

    public void init() {
        schema = new RecordSchema();
        schema.addField(new Field("a", FieldType.STRING));
        client.createTopic(projectName, topicName, 3, 3, RecordType.TUPLE, schema, "topic");
        GetTopicResult topic = client.getTopic(projectName, topicName);
        schema = topic.getRecordSchema();
    }

    public void putRecords() {
        ListShardResult shards = client.listShard(projectName, topicName);

        List<RecordEntry> recordEntries = new ArrayList<RecordEntry>();

        int recordNum = 10;

        for (int n = 0; n < recordNum; n++) {
            //RecordData
            RecordEntry entry = new RecordEntry(schema);

            for (int i = 0; i < entry.getFieldCount(); i++) {
                entry.setString(i, "");
            }

            //RecordShardId
            String shardId = shards.getShards().get(0).getShardId();

            entry.setShardId(shardId);
            entry.putAttribute("partition", "ds=2016");

            recordEntries.add(entry);
        }
        PutRecordsResult result = client.putRecords(projectName, topicName, recordEntries);

        //fail handle


        //handle failed records

    }

    public void getRecords() {
        ListShardResult shards = client.listShard(projectName, topicName);
        String shardId = shards.getShards().get(0).getShardId();

        GetCursorResult cursorRs = client.getCursor(projectName, topicName, shardId, System.currentTimeMillis() - 24 * 3600 * 1000 /* ms */);
        String cursor = cursorRs.getCursor();

        int limit = 10;
        while (true) {
            try {
                GetRecordsResult recordRs = client.getRecords(projectName, topicName, shardId, cursor, limit, schema);

                List<RecordEntry> recordEntries = recordRs.getRecords();

                if (cursor.equals(recordRs.getNextCursor())) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                cursor = recordRs.getNextCursor();
            } catch (InvalidCursorException ex) {
                cursorRs = client.getCursor(projectName, topicName, shardId, GetCursorRequest.CursorType.OLDEST);
                cursor = cursorRs.getCursor();
            }
        }
    }

    public static void main(String[] args) {
        DatahubExample example = new DatahubExample();
        try {
            example.init();
            example.putRecords();
            example.getRecords();
        } catch (DatahubClientException e) {
            e.printStackTrace();
        }
    }
}
