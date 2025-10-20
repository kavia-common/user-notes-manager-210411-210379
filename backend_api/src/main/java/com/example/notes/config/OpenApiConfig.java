package com.example.notes.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-API-DOCS
// User Story: Provide API documentation and discoverability.
// Acceptance Criteria: OpenAPI available at /api-docs and Swagger UI available.
// GxP Impact: YES - Documentation of interfaces.
// Risk Level: LOW
// Validation Protocol: VP-API-001
// ============================================================================
 */
@Configuration
public class OpenApiConfig {

    // PUBLIC_INTERFACE
    @Bean
    public OpenAPI notesOpenAPI() {
        /** Configure OpenAPI metadata and tags. */
        return new OpenAPI()
                .info(new Info()
                        .title("Notes Management API")
                        .description("REST API for managing user notes with GxP-compliant audit trail and RBAC hooks.")
                        .version("v1.0.0")
                        .contact(new Contact().name("Notes Team").email("support@example.com")))
                .addTagsItem(new Tag().name("Notes").description("CRUD operations for notes"))
                .addTagsItem(new Tag().name("System").description("System and documentation endpoints"))
                .externalDocs(new ExternalDocumentation().description("Service Docs").url("https://example.com/docs"));
    }
}
