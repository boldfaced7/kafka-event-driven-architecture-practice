package com.fastcampus.kafkahandson.ugc.chatgpt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatCompletionResponse(
        String id,
        String object,
        long created,
        String model,
        ChatChoice[] choices,
        Usage usage,
        @JsonProperty("system_fingerprint")
        String systemFingerprint
) {
    public record ChatChoice(
            int index,
            Message message,
            Object logprobs,
            @JsonProperty("finish_reason")
            String finishReason
    ) {
        public record Message(
                String role,
                String content
        ) {}
    }

    public record Usage(
            @JsonProperty("prompt_tokens")
            int promptTokens,
            @JsonProperty("completion_tokens")
            int completionTokens,
            @JsonProperty("total_tokens")
            int totalTokens
    ) {}
}