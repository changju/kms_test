package com.example.demo.aws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.kms.KmsAsyncClient;
import software.amazon.awssdk.services.kms.KmsAsyncClientBuilder;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.KmsClientBuilder;

@Configuration
public class AwsConfig {

    @Bean
    @Profile("test")
    public AwsCredentialsProvider testCredential() {
        return ProfileCredentialsProvider.create();
    }

    @Bean
    public KmsClient testKmsClient() {
        return KmsClient.builder().build();
    }
}
