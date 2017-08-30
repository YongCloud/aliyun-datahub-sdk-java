package com.aliyun.datahub;

import com.aliyun.datahub.auth.Account;
import com.aliyun.datahub.auth.AliyunAccount;
import com.aliyun.datahub.common.data.RecordType;
import com.aliyun.datahub.exception.AbortedException;
import com.aliyun.datahub.exception.AuthorizationFailureException;
import com.aliyun.datahub.util.DatahubTestUtils;
import org.testng.annotations.Test;

/**
 * Time:    2017-05-11 4:22 pm
 * Author:  jingshan.mjs@alibaba-inc.com
 */
@Test
public class SecurityTest {
    @Test(expectedExceptions = AuthorizationFailureException.class)
    public void testCreateTopicWithInvalidAccessKey() {
        String accessId = DatahubTestUtils.getAccessId();
        String accessKey = "invalid accesskey";
        Account account = new AliyunAccount(accessId, accessKey);
        String endpoint = DatahubTestUtils.getEndpoint();
        if (endpoint == null || endpoint.isEmpty()) {
            throw new AbortedException("endpoint not set!");
        }
        DatahubConfiguration conf = new DatahubConfiguration(account, endpoint);
        DatahubClient client = new DatahubClient(conf);
        client.createTopic(DatahubTestUtils.getProjectName(), "test_project", 1, 1, RecordType.BLOB, "topic");
    }
}
