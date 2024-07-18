package io.camunda.example;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.generator.java.annotation.ElementTemplate;
import io.camunda.example.dto.ReqResConnectorRequest;
import io.camunda.example.dto.ReqResConnectorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@OutboundConnector(
        name = "REQRESCONNECTOR",
        inputVariables = {"page","per_page"},
        type = "io.camunda:reqres:1")
@ElementTemplate(
        id = "io.camunda.connector.ReqRes.v1",
        name = "ReqRes Connector",
        version = 1,
        description = "Helps to connect to the reqres API",
        icon = "icon.svg",
        documentationRef = "https://reqres.in/",
        propertyGroups = {
                @ElementTemplate.PropertyGroup(id = "pagination", label = "Pagination")
        },
        inputDataClass = ReqResConnectorRequest.class)
public class ReqResConnectorFunction implements OutboundConnectorFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReqResConnectorFunction.class);

    @Override
    public Object execute(OutboundConnectorContext outboundConnectorContext) throws Exception {
        final var connectorRequest = outboundConnectorContext.bindVariables(ReqResConnectorRequest.class);
        return executeConnector(connectorRequest);
    }

    private ReqResConnectorResult executeConnector(final ReqResConnectorRequest connectorRequest) {
        LOGGER.info("Executing reqres connector with request {}", connectorRequest);
        String page = connectorRequest.page();
        String perPage = connectorRequest.per_page();
        if (page == null || page.isEmpty()) {
            throw new ConnectorException("FAIL", "page value was " + page);
        }

        URI uri = URI.create("https://reqres.in/api/users?page=" + page + "&per_page=" + perPage);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        try (HttpClient httpClient = HttpClient.newHttpClient()) {

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            LOGGER.info("Status Code: " + httpResponse.statusCode());
            LOGGER.info("Response Body: " + httpResponse.body());

            return new ReqResConnectorResult("Response received: " + httpResponse.body());

        } catch (Exception e) {
              LOGGER.error(e.getMessage());
            throw new ConnectorException("FAIL", e.getMessage());
        }
    }
}
