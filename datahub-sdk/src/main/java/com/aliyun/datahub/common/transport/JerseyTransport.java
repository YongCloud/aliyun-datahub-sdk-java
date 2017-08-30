package com.aliyun.datahub.common.transport;

import com.aliyun.datahub.DatahubConfiguration;
import com.aliyun.datahub.exception.DatahubClientException;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Variant;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.glassfish.jersey.client.RequestEntityProcessing.BUFFERED;

/**
 * Author:  jingshan.mjs@alibaba-inc.com
 */
public class JerseyTransport implements Transport {
    private static final Logger log = Logger.getLogger(JerseyTransport.class.getName());
    private JerseyClient jerseyClient;
    private DatahubConfiguration conf;

    public JerseyTransport(DatahubConfiguration conf) {
        this.conf = conf;
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(ClientProperties.REQUEST_ENTITY_PROCESSING, BUFFERED);
        clientConfig.property(ClientProperties.READ_TIMEOUT, conf.getSocketTimeout() * 1000);
        clientConfig.property(ClientProperties.CONNECT_TIMEOUT, conf.getSocketConnectTimeout() * 1000);
        PoolingHttpClientConnectionManager connectionManager =
                new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(conf.getTotalConnections());
        connectionManager.setDefaultMaxPerRoute(conf.getConnectionsPerEndpoint());
        clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);
        clientConfig.connectorProvider(new ApacheConnectorProvider());

        jerseyClient = JerseyClientBuilder.createClient(clientConfig);
    }

    @Override
    public Response request(DefaultRequest req, String endpoint) throws IOException {
        String dhEndpoint = conf.getEndpoint();
        if (endpoint != null && !endpoint.isEmpty()) {
            dhEndpoint = endpoint;
        }
        String url = dhEndpoint + req.getResource();
        if (log.isLoggable(Level.FINE)) {
            log.fine("Connectiong to " + url);
        }

        Invocation.Builder builder = jerseyClient.target(url).request();

        if (log.isLoggable(Level.FINE)) {
            log.fine("Request headers: " + req.getHeaders().toString());
        }

        for (Map.Entry<String, String> entry : req.getHeaders().entrySet()) {
            if (!entry.getKey().equals(Headers.CONTENT_LENGTH)) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }

        javax.ws.rs.core.Response resp = null;

        switch (req.getHttpMethod()) {
            case POST:
                resp = builder.post(Entity.entity(req.getBody(), new Variant(MediaType.APPLICATION_JSON_TYPE, (String) null, req.getHeaders().get(Headers.CONTENT_ENCODING))));
                break;
            case PUT:
                resp = builder.put(Entity.entity(req.getBody(), new Variant(MediaType.APPLICATION_JSON_TYPE, (String) null, req.getHeaders().get(Headers.CONTENT_ENCODING))));
                break;
            case GET:
                resp = builder.get();
                break;
            case DELETE:
                resp = builder.delete();
                break;
            case HEAD:
                resp = builder.head();
                break;
        }

        if (resp == null) {
            throw new IOException("request to server failed");
        }
        DefaultResponse response = new DefaultResponse();
        response.setStatus(resp.getStatus());
        for (String key : resp.getHeaders().keySet()) {
            response.setHeader(key, resp.getHeaderString(key));
        }
        response.setBody(resp.readEntity(byte[].class));

        resp.close();

        return response;
    }

    @Override
    public Response request(DefaultRequest req) throws IOException {
        return request(req, null);
    }

    @Override
    public Connection connect(DefaultRequest req) throws IOException {
        throw new DatahubClientException("not implemented");
    }

    @Override
    public void close() {
        jerseyClient.close();
    }
}
