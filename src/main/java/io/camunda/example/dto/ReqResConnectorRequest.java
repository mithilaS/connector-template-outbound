package io.camunda.example.dto;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyType;
import jakarta.validation.constraints.NotEmpty;

public record ReqResConnectorRequest(
        @TemplateProperty(group = "pagination", type = PropertyType.Text) String page,
        @TemplateProperty(group = "pagination", type = PropertyType.Text) String per_page) {
}
