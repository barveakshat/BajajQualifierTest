package com.example.bajajQualifier;

import com.example.bajajQualifier.model.GenerateWebhookRequest;
import com.example.bajajQualifier.model.GenerateWebhookResponse;
import com.example.bajajQualifier.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BajajQualifierApplication implements CommandLineRunner {

	private final WebhookService webhookService;

	public static void main(String[] args) {
		SpringApplication.run(BajajQualifierApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Step 1: Calling GenerateWebhook API
		GenerateWebhookRequest request = new GenerateWebhookRequest(
				"Akshat Barve",
				"22BSA10066",
				"akshatbarve2022@vitbhopal.ac.in"
		);

		GenerateWebhookResponse response = webhookService.generateWebhook(request);
		System.out.println("Webhook generated: " + response);

		// Step 2: Submitting SQL solution
		String submissionResponse = webhookService.submitSolution(
				response.getWebhook(),
				response.getAccessToken(),
				request.getRegNo()
		);

		System.out.println("Submission Response: " + submissionResponse);
	}
}
