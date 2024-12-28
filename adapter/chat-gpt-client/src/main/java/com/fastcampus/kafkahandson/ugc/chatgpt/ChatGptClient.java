package com.fastcampus.kafkahandson.ugc.chatgpt;

import com.fastcampus.kafkahandson.ugc.CustomObjectMapper;
import com.fastcampus.kafkahandson.ugc.chatgpt.model.ChatCompletionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class ChatGptClient {

    public static final String TARGET_GPT_MODEL = "gpt-3.5-turbo";

    @Value("${OPENAI_API_KEY}")
    private String openaiApiKey;

    private final WebClient chatGptWebClient;
    private final CustomObjectMapper objectMapper = new CustomObjectMapper();

    public ChatGptClient(
            @Qualifier("chatGptWebClient")
            WebClient chatGptWebClient
    ) {
        this.chatGptWebClient = chatGptWebClient;
    }

    public String getResult(
            String content,
            ChatPolicy chatPolicy
    ) {
        return chatGptWebClient
                .post()
                .uri("/v1/chat/completions")
                .header("Authorization", "Bearer " + openaiApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(buildRequestBody(content, chatPolicy))
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractContentFromJsonResponse)
                .block();
    }

    private Map<String, Object> buildRequestBody(String content, ChatPolicy chatPolicy) {
        return Map.of(
                "model", TARGET_GPT_MODEL,
                "messages", List.of(
                        Map.of("role", "system", "content", chatPolicy.instruction()),
                        Map.of("role", "user", "content", chatPolicy.exampleContent()),
                        Map.of("role", "assistant", "content", chatPolicy.exampleInspectionResult()),
                        Map.of("role", "user", "content", content)
                ),
                "stream", false
        );
    }

    private String extractContentFromJsonResponse(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, ChatCompletionResponse.class)
                    .choices()[0].message().content();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public record ChatPolicy(
       String instruction,
       String exampleContent,
       String exampleInspectionResult
    ) {}
}
