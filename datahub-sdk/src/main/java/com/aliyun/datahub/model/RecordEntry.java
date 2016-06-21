package com.aliyun.datahub.model;

import com.aliyun.datahub.DatahubConstants;
import com.aliyun.datahub.common.data.Field;
import com.aliyun.datahub.common.data.FieldType;
import com.aliyun.datahub.common.data.RecordSchema;
import com.aliyun.datahub.common.util.JacksonParser;
import com.aliyun.datahub.exception.DatahubClientException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

public class RecordEntry {
    private String partitionKey;
    private String hashKey;
    private String shardId;
    private Map<String, String> attributes = new HashMap<String, String>();
    private long systemTime;
    private Field[] fields;
    private Object[] values;
    private HashMap<String, Integer> nameMap = new HashMap<String, Integer>();

    private static final String STRING_CHARSET = "UTF-8";
    // 9999-12-31 23:59:59
    private static final long DATETIME_MAX_TICKS = 253402271999000000L;
    // 0001-01-01 00:00:00
    private static final long DATETIME_MIN_TICKS = -62135798400000000L;

    public RecordEntry(RecordSchema schema) {
        if (schema == null) {
            throw new IllegalArgumentException("schema must not be null");
        }
        init(schema.getFields().toArray(new Field[0]));
    }

    public RecordEntry(Field[] fields) {
        if (fields == null) {
            throw new IllegalArgumentException("field list must not be null");
        }
        init(fields);
    }

    private void init(Field [] fields) {
        this.fields = fields;

        values = new Object[fields.length];

        for (int i = 0; i < fields.length; i++) {
            nameMap.put(fields[i].getName(), i);
        }
    }

    public long getRecordSize() {
        long len = 0;
        for (int i = 0; i < values.length; ++i) {
            if (values[i] == null) {
                continue;
            }
            Field field = fields[i];
            if (field.getType() == FieldType.BIGINT) {
                len += 8;
            } else if (field.getType() == FieldType.BOOLEAN) {
                len += 4;
            } else if (field.getType() == FieldType.DOUBLE) {
                len += 8;
            } else if (field.getType() == FieldType.TIMESTAMP) {
                len += 8;
            } else if (field.getType() == FieldType.STRING) {
                len += ((String) values[i]).length();
            } else {
                throw new IllegalArgumentException("unknown record type :" + field.getType().name());
            }
        }
        return len;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public int getFieldCount() {
        return values.length;
    }

    public Field[] getFields() {
        return fields;
    }

    private Object get(int idx) {
        return values[idx];
    }

    private Object get(String columnName) {
        return values[getFieldIndex(columnName)];
    }

    public void setBigint(int idx, Long value) {
        if (value != null && (value > Long.MAX_VALUE || value <= Long.MIN_VALUE)) {
            throw new IllegalArgumentException("InvalidData: Bigint out of range.");
        }
        values[idx] = value;
    }


    public Long getBigint(int idx) {
        return (Long) get(idx);
    }


    public void setBigint(String columnName, Long value) {
        setBigint(getFieldIndex(columnName), value);
    }


    public Long getBigint(String columnName) {
        return (Long) get(columnName);
    }


    public void setDouble(int idx, Double value) {
        values[idx] = value;
    }


    public Double getDouble(int idx) {
        return (Double) get(idx);
    }


    public void setDouble(String columnName, Double value) {
        setDouble(getFieldIndex(columnName), value);
    }


    public Double getDouble(String columnName) {
        return (Double) get(columnName);
    }

    public void setBoolean(int idx, Boolean value) {
        values[idx] = value;
    }


    public Boolean getBoolean(int idx) {
        return (Boolean) get(idx);
    }


    public void setBoolean(String columnName, Boolean value) {
        setBoolean(getFieldIndex(columnName), value);
    }


    public Boolean getBoolean(String columnName) {
        return (Boolean) get(columnName);
    }

    public void setTimeStamp(int idx, Long value) {
        if (value != null && (value > Long.MAX_VALUE || value <= Long.MIN_VALUE)) {
            throw new IllegalArgumentException("InvalidData: timestamp out of range.");
        }
        values[idx] = value;
    }


    public Long getTimeStamp(int idx) {
        return (Long) get(idx);
    }


    public void setTimeStamp(String columnName, Long value) {
        setTimeStamp(getFieldIndex(columnName), value);
    }


    public Long getTimeStamp(String columnName) {
        return (Long) get(columnName);
    }

    public void setString(int idx, String value) {
        values[idx] = value;
    }


    public String getString(int idx) {
        Object o = get(idx);
        if (o == null) {
            return null;
        }
        return (String) o;
    }


    public void setString(String columnName, String value) {
        setString(getFieldIndex(columnName), value);
    }


    public String getString(String columnName) {
        return getString(getFieldIndex(columnName));
    }

    public int getFieldIndex(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Field name is null");
        }
        Integer idx = nameMap.get(name.toLowerCase());
        if (idx == null) {
            throw new IllegalArgumentException("No such column:" + name.toLowerCase());
        }
        return idx;
    }

    public void clear() {
        for (int i = 0; i < values.length; i++) {
            values[i] = null;
        }
    }

    public String getShardId() {
        return shardId;
    }

    public void setShardId(String shardId) {
        this.shardId = shardId;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public Map<String, String> getAttributes() {
        return new HashMap<String, String>(attributes);
    }

    public void putAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public JsonNode toJsonNode() {
        ObjectMapper mapper = JacksonParser.getObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        if (shardId != null && !shardId.isEmpty()) {
            node.put(DatahubConstants.ShardId, shardId);
        } else if (partitionKey != null && !partitionKey.isEmpty()) {
            node.put(DatahubConstants.PartitionKey, partitionKey);
        } else if (hashKey != null && !hashKey.isEmpty()) {
            node.put(DatahubConstants.HashKey, hashKey);
        } else {
            throw new DatahubClientException("Parameter shardId/partitionKey/hashKey not set.");
        }

        ObjectNode attr = node.putObject("Attributes");
        for (String key : attributes.keySet()) {
            attr.put(key, attributes.get(key));
        }

        ArrayNode record = node.putArray("Data");
        for (int i = 0; i < this.getFieldCount(); i++) {
            if (this.get(i) != null) {
                record.add(String.valueOf(this.get(i)));
            } else {
                record.add((JsonNode)null);
            }
        }

        return node;
    }
}
