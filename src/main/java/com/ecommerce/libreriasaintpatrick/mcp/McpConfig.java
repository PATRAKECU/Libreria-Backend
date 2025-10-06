package com.ecommerce.libreriasaintpatrick.mcp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class McpConfig {

    @Value("${mcp.base-url}")
    private String baseUrl;

    public List<McpTool> getTools() {
        List<McpTool> tools = new ArrayList<>();

        tools.add(new McpTool(
                "listarLibros",
                "Devuelve la lista de libros disponibles",
                "GET",
                baseUrl + "/api/libros",
                Map.of()
        ));

        tools.add(new McpTool(
                "consultarLibro",
                "Devuelve los detalles de un libro por su ID",
                "GET",
                baseUrl + "/api/libros/{id}",
                Map.of("id", Map.of("type", "integer"))
        ));

        tools.add(new McpTool(
                "listarCafes",
                "Devuelve la lista de cafés disponibles",
                "GET",
                baseUrl + "/api/cafes",
                Map.of()
        ));

        tools.add(new McpTool(
                "consultarCafe",
                "Devuelve los detalles de un café por su ID",
                "GET",
                baseUrl + "/api/cafes/{id}",
                Map.of("id", Map.of("type", "integer"))
        ));

        tools.add(new McpTool(
                "listarSeparadores",
                "Devuelve la lista de separadores disponibles",
                "GET",
                baseUrl + "/api/separadores",
                Map.of()
        ));

        tools.add(new McpTool(
                "consultarSeparador",
                "Devuelve los detalles de un separador por su ID",
                "GET",
                baseUrl + "/api/separadores/{id}",
                Map.of("id", Map.of("type", "integer"))
        ));

        tools.add(new McpTool(
                "listarSoportes",
                "Devuelve la lista de soportes disponibles",
                "GET",
                baseUrl + "/api/soportes",
                Map.of()
        ));

        tools.add(new McpTool(
                "consultarSoporte",
                "Devuelve los detalles de un soporte por su ID",
                "GET",
                baseUrl + "/api/soportes/{id}",
                Map.of("id", Map.of("type", "integer"))
        ));

        tools.add(new McpTool(
                "agregarAlCarrito",
                "Agrega los productos especificados al carrito",
                "POST",
                baseUrl + "/api/pedidos/carrito/agregar",
                Map.of(
                        "productoId", Map.of("type", "integer"),
                        "cantidad", Map.of("type", "integer")
                )
        ));

        tools.add(new McpTool(
                "crearPedido",
                "Genera un pedido con los productos del carrito",
                "POST",
                baseUrl + "/api/pedidos/carrito/pagar",
                Map.of()
        ));

        return tools;
    }
}