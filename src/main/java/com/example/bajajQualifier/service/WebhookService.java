package com.example.bajajQualifier.service;

import com.example.bajajQualifier.model.GenerateWebhookRequest;
import com.example.bajajQualifier.model.GenerateWebhookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final WebClient.Builder webClientBuilder;
    private final SqlSolverService sqlSolverService;

    public GenerateWebhookResponse generateWebhook(GenerateWebhookRequest request) {
        WebClient client = webClientBuilder.build();

        return client.post()
                .uri("https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GenerateWebhookResponse.class)
                .block();
    }

    public String submitSolution(String webhookUrl, String token, String regNo) {
        WebClient client = webClientBuilder.build();

        // Getting final SQL query
        String finalQuery = sqlSolverService.solveSQL(regNo);

        return client.post()
                .uri(webhookUrl)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SubmitRequest(finalQuery))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // Inner class for submission body
    private record SubmitRequest(String finalQuery) {}
}

