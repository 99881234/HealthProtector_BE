package com.graduate.HealthProtector.protector.config;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.core.GoogleCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class GoogleCloudConfig {

    @Value("${google.cloud.api.key}")
    private String apiKey;

    @Bean
    public SpeechClient speechClient() throws IOException {
        GoogleCredentialsProvider credentials = ServiceAccountCredentials.fromStream(new ByteArrayInputStream(apiKey.getBytes()));
        SpeechSettings speechSettings = SpeechSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        return SpeechClient.create(speechSettings);
    }
}
