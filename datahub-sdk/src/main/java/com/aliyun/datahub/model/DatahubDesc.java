package com.aliyun.datahub.model;

import com.aliyun.datahub.common.util.JacksonParser;
import com.aliyun.datahub.exception.DatahubClientException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;


/**
 * Time:    2017-04-10 11:13
 * Author:  jingshan.mjs@alibaba-inc.com
 */
public class DatahubDesc extends ConnectorConfig {
    private String endpoint;
    private String project;
    private String topic;

    // DATAHUB config
    public final String DATAHUB_PROJECT = "Project";
    public final String DATAHUB_TOPIC = "Topic";
    public final String DATAHUB_ENDPOINT = "DatahubEndpoint";

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public ObjectNode toJsonNode() {
        if (endpoint == null || endpoint.isEmpty()) {
            throw new DatahubClientException("endpoint is null");
        } else if (project == null || project.isEmpty()) {
            throw new DatahubClientException("project is null");
        } else if (topic == null || topic.isEmpty()) {
            throw new DatahubClientException("topic is null");
        }

        ObjectMapper mapper = JacksonParser.getObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        rootNode.put(DATAHUB_ENDPOINT, endpoint);
        rootNode.put(DATAHUB_PROJECT, project);
        rootNode.put(DATAHUB_TOPIC, topic);

        return rootNode;
    }

    @Override
    public void ParseFromJsonNode(JsonNode node) {
        if (node != null && !node.isNull()) {
            JsonNode config = node.get(DATAHUB_ENDPOINT);
            if (config != null && !config.isNull()) {
                setEndpoint(config.asText());
            }

            config = node.get(DATAHUB_PROJECT);
            if (config != null && !config.isNull()) {
                setProject(config.asText());
            }

            config = node.get(DATAHUB_TOPIC);
            if (config != null && !config.isNull()) {
                setTopic(config.asText());
            }

        } else {
            throw new DatahubClientException("Invalid response, missing config.");
        }
    }
}
