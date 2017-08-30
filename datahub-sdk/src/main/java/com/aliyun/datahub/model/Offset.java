package com.aliyun.datahub.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import com.aliyun.datahub.common.util.JacksonParser;

public class Offset {
    private long sequence;
    private long timestamp;

    public Offset(long seq, long ts) {
        this.sequence = seq;
        this.timestamp = ts;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public ObjectNode toObjectNode() {
        ObjectMapper mapper = JacksonParser.getObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("Sequence", sequence);
        node.put("Timestamp", timestamp);
        return node;
    }
}
