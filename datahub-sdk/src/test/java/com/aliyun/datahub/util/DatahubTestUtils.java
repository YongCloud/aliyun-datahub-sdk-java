package com.aliyun.datahub.util;

import com.aliyun.datahub.DatahubClient;
import com.aliyun.datahub.DatahubConfiguration;
import com.aliyun.datahub.auth.Account;
import com.aliyun.datahub.auth.AliyunAccount;
import com.aliyun.datahub.common.data.Field;
import com.aliyun.datahub.common.data.FieldType;
import com.aliyun.datahub.common.data.RecordSchema;
import com.aliyun.datahub.exception.AbortedException;
import com.aliyun.datahub.model.RecordEntry;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
@Test
public class DatahubTestUtils {

    private static final Properties props = new Properties();
    private static AtomicLong counter = new AtomicLong();

    public final static Long MIN_TIMESTAMP_VALUE = -62135798400000000L;
    public final static Long MAX_TIMESTAMP_VALUE = 253402271999000000L;
    public final static int MAX_STRING_LENGTH = 1024 * 1024;

    static {
        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从CLASSPATH加载test.properties
     *
     * @return
     * @throws IOException
     */
    public static Properties loadConfig() throws IOException {

        InputStream is = null;
        try {
            is = DatahubTestUtils.class.getClassLoader().getResourceAsStream(
                    "datahub_test.conf");
            props.load(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return props;
    }

    public static DatahubConfiguration getConf() {
        String accessId = props.getProperty("default.access.id");
        String accessKey = props.getProperty("default.access.key");
        String securityToken = props.getProperty("default.sts.token");
        Account account = new AliyunAccount(accessId, accessKey, securityToken);
        String endpoint = props.getProperty("default.endpoint");
        if (endpoint == null || endpoint.isEmpty()) {
            throw new AbortedException("default.endpoint not set!");
        }
        return new DatahubConfiguration(account, endpoint);
    }

    public static String getProjectName() {
        return props.getProperty("default.project");
    }

    public static String getRandomTopicName() {
        return "ut_topic_" + System.currentTimeMillis() + "_" + counter.addAndGet(1);
    }

    public static DatahubClient getDefaultClient() {
        DatahubConfiguration conf = getConf();
        return new DatahubClient(conf);
    }

    /**
     * create schema by spec
     *
     * @param spec format: type name, type namne ...
     * @return
     */
    public static RecordSchema createSchema(String spec) {
        RecordSchema schema = new RecordSchema();
        String[] segs = spec.trim().split(",");
        for (String seg : segs) {
            String[] pair = seg.trim().split(" +");
            if (pair.length != 2) {
                throw new IllegalArgumentException("invalid spec");
            }
            String type = pair[0];
            String name = pair[1];
            schema.addField(new Field(name, FieldType.valueOf(type.toUpperCase())));
        }
        return schema;
    }

    public static String getRandomString(int size) {
        return RandomStringUtils.randomAscii(size);
    }

    public static String getRandomString() {
        return getRandomString(new Random().nextInt(32));
    }

    public static Long getRandomNumber() {
        return new Random().nextLong();
    }

    public static Long getRandomTimestamp() {
        return new RandomDataGenerator().nextLong(MIN_TIMESTAMP_VALUE, MAX_TIMESTAMP_VALUE);
    }

    public static Boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }

    public static Double getRandomDecimal() {
        return new Random().nextDouble() + new Random().nextLong();
    }

    public static RecordEntry makeRecord(RecordSchema schema) {
        RecordEntry entry = new RecordEntry(schema);
        for (int i = 0; i < schema.getFields().size(); ++i) {
            if (schema.getField(i).getType() == FieldType.BIGINT) {
                entry.setBigint(i, getRandomNumber());
            } else if (schema.getField(i).getType() == FieldType.BOOLEAN) {
                entry.setBoolean(i, getRandomBoolean());
            } else if (schema.getField(i).getType() == FieldType.STRING) {
                entry.setString(i, getRandomString());
            } else if (schema.getField(i).getType() == FieldType.DOUBLE) {
                entry.setDouble(i, getRandomDecimal());
            } else {
                entry.setTimeStamp(i, getRandomNumber());
            }
        }
        return entry;
    }
}
