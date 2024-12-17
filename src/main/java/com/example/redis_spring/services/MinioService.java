package com.example.redis_spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final WebClient.Builder webClientBuilder;

    private final String MINIO_API_URL = "http://localhost:8083/minio-api/api/files/"; // URL вашего MinIO API


    private final RestTemplate restTemplate;



    public String uploadFile(byte[] fileData, String filename) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(fileData) {
            @Override
            public String getFilename() {
                return filename;
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(MINIO_API_URL, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new Exception("Ошибка при загрузке файла на MinIO: " + response.getStatusCode());
        }
    }

    public String getFiles() {
        return webClientBuilder.build()
                .get()
                .uri(MINIO_API_URL)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
