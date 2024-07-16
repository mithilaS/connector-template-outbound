package io.camunda.example.dto;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyType;
import jakarta.validation.constraints.NotEmpty;

public record ReqResConnectorRequest(
        @NotEmpty @TemplateProperty(group = "pagination", type = PropertyType.Text)int page,
        @NotEmpty @TemplateProperty(group = "pagination", type = PropertyType.Text) int perPage) {
}
