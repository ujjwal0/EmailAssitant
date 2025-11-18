package com.email.writter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmailGeneratorService {
    private final WebClient webClient;
    private final String apiKey;

    public  EmailGeneratorService(WebClient.Builder webClientBuilder,@Value("${gemini.api.url}") String baseURL,@Value("${gemini.api.key}") String geminiAPIKEY){
        this.apiKey=geminiAPIKEY;
        this.webClient=webClientBuilder.baseUrl(baseURL).build();
    }
    public String generateEmailReply(EmailRequest emailRequest) {

        //Buid Prompt
        String prompt=buildPrompt(emailRequest);
        //prepare Raw json
        String requestBody=String.format("""
                {
                "contents": [
                      {
                        "parts": [
                          {
                            "text": "%s"
                          }
                        ]
                      }
                    ]
                }
                """,prompt);
        //send Request
        //Extract Response

    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt= new StringBuilder();
        prompt.append("Generate a professional email reply for the following email:");
        if(emailRequest.getTone()!=null && !emailRequest.getTone().isEmpty()){
            prompt.append("Use a ").append(emailRequest.getTone()).append("tone.");
        }
        prompt.append("Original email: \n").append(emailRequest.getEmailContent());
        return prompt.toString();
    }
}
