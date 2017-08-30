package com.aliyun.datahub.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.datahub.DatahubClient;
import com.aliyun.datahub.DatahubConfiguration;
import com.aliyun.datahub.auth.AliyunAccount;
import com.aliyun.datahub.common.data.RecordSchema;
import com.aliyun.datahub.exception.DatahubClientException;
import com.aliyun.datahub.model.GetCursorResult;
import com.aliyun.datahub.model.GetRecordsResult;
import com.aliyun.datahub.model.GetSubscriptionOffsetResult;
import com.aliyun.datahub.model.GetTopicResult;
import com.aliyun.datahub.model.ListShardResult;
import com.aliyun.datahub.model.Offset;
import com.aliyun.datahub.model.RecordEntry;
import com.aliyun.datahub.model.GetCursorRequest.CursorType;

class Consumer extends Thread {
	private String projectName = null;
	private String topicName = null;
	private String subId = null;
	private String shardId = null;
	private RecordSchema schema = null;
	private DatahubClient client = null;

	public Consumer(String projectName, String topicName, String subId, String shardId, RecordSchema schema, DatahubConfiguration conf) {
		this.projectName = projectName;
		this.topicName = topicName;
		this.subId = subId;
		this.shardId = shardId;
		this.schema = schema;
		this.client = new DatahubClient(conf);
	}
	
	private void commit(Offset offset) {
		Map<String,  Offset> offsets = new HashMap<String, Offset>();
		offsets.put(shardId, offset);
		client.commitSubscriptionOffset(projectName, subId, offsets);
	}

	@Override
	public void run() {
		try {
			boolean bExit = false;
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
			
			try {
				long recordNum = 0L;
				while (!bExit) {
					GetRecordsResult recordResult = client.getRecords(projectName, topicName, shardId, cursor, 10, schema);
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
				commit(offset);
				System.out.println("Consume shard:" + shardId + " thread exit, commit offet finally!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
public class SubscriptionExample {
    private String accessId = "a8AMJHS2CTQhlhsH";
    private String accessKey = "hQzeuDQ5wNkDPmtVkH8kOinQZh68P7";
    private String endpoint = "http://10.101.214.153:8089";
    private String projectName = "subscription_project_test";
    private String topicName = "sub_topic_test";
    private String subId = "14981243082759tkpx";
    private DatahubConfiguration conf;
    private DatahubClient client;

    public SubscriptionExample() {
        this.conf = new DatahubConfiguration(new AliyunAccount(accessId, accessKey), endpoint);
        this.client = new DatahubClient(conf);
    }

    public void Start() {
    	GetTopicResult topicResult = client.getTopic(projectName, topicName);
    	ListShardResult shardResult = client.listShard(projectName, topicName);
    	List<Consumer> threadList = new ArrayList<Consumer>();
    	for (int i = 0; i < shardResult.getShards().size(); ++i) {
    		threadList.add(new Consumer(projectName, topicName, subId, shardResult.getShards().get(i).getShardId(), topicResult.getRecordSchema(), conf));
			threadList.get(i).start();
		}
    	for (Thread t : threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted!");
			}
		}
    }

    public static void main(String[] args) {
    	SubscriptionExample example = new SubscriptionExample();
        try {
            example.Start();
        } catch (DatahubClientException e) {
            e.printStackTrace();
        }
    }
}