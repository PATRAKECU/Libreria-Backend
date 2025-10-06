package com.ecommerce.libreriasaintpatrick.mcp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class McpTool {
    private String name;
    private String description;
    private String method;
    private String url;
    private Map<String, Object> parameters;
}