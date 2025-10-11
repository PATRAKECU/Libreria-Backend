package com.ecommerce.libreriasaintpatrick.service;

import com.ecommerce.libreriasaintpatrick.dto.producto.ProductoGeminiDto;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeminiService {

    @Value("${gcloud.project.id}")
    private String projectId;

    @Value("${gcloud.project.location}")
    private String location;

    private final ProductoService productoService;

    public GeminiService(ProductoService productService) {
        this.productoService = productService;
    }

    public String getGeminiResponse(String userMessage) throws IOException {
        List<ProductoGeminiDto> products = productoService.obtenerProductosParaGemini();
        String productContext = products.stream()
                .map(p -> String.format("ID: %d, Name: %s, Description: %s, Price: %.2f",
                        p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio()))
                .collect(Collectors.joining("\n"));

        String prompt = String.format(
                "You are an AI assistant for an e-commerce store called Librería Saint Patrick. " +
                        "Your goal is to help users with product-related queries. " +
                        "Here are the products available in the store:\n%s\n\nUser query: %s\n\n" +
                        "Based on the available products, please answer the user's query. " +
                        "If the query is not related to products, politely state that you can only assist with product-related questions.",
                productContext, userMessage
        );

        try (VertexAI vertexAi = new VertexAI(projectId, location)) {
            GenerativeModel model = new GenerativeModel("gemini-2.5-flash", vertexAi);
            GenerateContentResponse response = model.generateContent(prompt);
            return ResponseHandler.getText(response);
        }
    }
}
