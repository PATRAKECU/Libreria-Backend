package com.ecommerce.libreriasaintpatrick.controller;

import com.ecommerce.libreriasaintpatrick.dto.chatbot.ChatRequest;
import com.ecommerce.libreriasaintpatrick.dto.chatbot.ChatResponse;
import com.ecommerce.libreriasaintpatrick.service.GeminiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final GeminiService geminiService;

    public ChatController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            String respuesta = geminiService.getGeminiResponse(request.getMessage());
            return ResponseEntity.ok(new ChatResponse(respuesta));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatResponse("Error al generar respuesta: " + e.getMessage()));
        }
    }
}
