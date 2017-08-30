package com.aliyun.datahub.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.datahub.DatahubClient;
import com.aliyun.datahub.DatahubConfiguration;
import com.aliyun.datahub.auth.AliyunAccount;
import com.aliyun.datahub.exception.DatahubClientException;
import com.aliyun.datahub.model.GetCursorResult;
import com.aliyun.datahub.model.GetRecordsResult;
import com.aliyun.datahub.model.GetSubscriptionOffsetResult;
import com.aliyun.datahub.model.GetTopicResult;
import com.aliyun.datahub.model.Offset;
import com.aliyun.datahub.model.RecordEntry;
import com.aliyun.datahub.model.GetCursorRequest.CursorType;

public class SingleSubscriptionExample {
    private String accessId = "a8AMJHS2CTQhlhsH";
    private String accessKey = "hQzeuDQ5wNkDPmtVkH8kOinQZh68P7";
    private String endpoint = "http://10.101.214.153:8089";
    private String projectName = "subscription_project_test";
    private String topicName = "sub_topic_test";
    private String subId = "1498124416867hSbA9";
    private String shardId = "0";
    private DatahubConfiguration conf;
    private DatahubClient client;

    public SingleSubscriptionExample() {
        this.conf = new DatahubConfiguration(new AliyunAccount(accessId, accessKey), endpoint);
        this.client = new DatahubClient(conf);
    }
	
	private void commit(Offset offset) {
		Map<String,  Offset> offsets = new HashMap<String, Offset>();
		offsets.put(shardId, offset);
		client.commitSubscriptionOffset(projectName, subId, offsets);
	}

    public void Start() {
		try {
			boolean bExit = false;
			GetTopicResult topicResult = client.getTopic(projectName, topicName);
			// 先获取订阅的点位信息
			GetSubscriptionOffsetResult offsetResult = client.getSubscriptionOffset(projectName, subId);
			Offset offset = offsetResult.getOffsets().get(shardId);
			if (null == offset) {
				// 该shard之前没有存过任何点位，获取初始点位
				GetCursorResult cursorResult = client.getCursor(projectName, topicName, shardId, CursorType.OLDEST);
				offset = new Offset(cursorResult.getSequence(), cursorResult.getRecordTime());
			}
			
			// 将点位信息换取cursor
			GetCursorResult cursorResult = client.getCursor(projectName, topicName, shardId, CursorType.SEQUENCE, offset.getSequence());
			String cursor = cursorResult.getCursor();
			
			System.out.println("Start consume shard:" + shardId + ", start offset:" + offset.toObjectNode().toString() + ", cursor:" + cursor);
			
			long recordNum = 0L;
			try {
				while (!bExit) {
					GetRecordsResult recordResult = client.getRecords(projectName, topicName, shardId, cursor, 10, topicResult.getRecordSchema());
					List<RecordEntry> records = recordResult.getRecords();
					if (records.size() == 0) {
						bExit = true;
					} else {
						for (RecordEntry record : records) {
							// 处理记录逻辑
							System.out.println("Consume shard:" + shardId + " thread process record:" + record.toJsonNode().toString());
							
							// 上报点位，该示例是每处理100条记录上报一次点位
							offset = record.getOffset();
							recordNum++;
							if (recordNum % 100 == 0) {
								commit(offset);
							}
						}
						cursor = recordResult.getNextCursor();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 确保最后一次消费点位上报
				if (recordNum % 100 != 0) {
					commit(offset);
					System.out.println("Consume shard:" + shardId + " thread exit, commit offet finally!");
				}
				System.out.print("Total Consume record num:" + recordNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
    	SingleSubscriptionExample example = new SingleSubscriptionExample();
        try {
            example.Start();
        } catch (DatahubClientException e) {
            e.printStackTrace();
        }
    }
}