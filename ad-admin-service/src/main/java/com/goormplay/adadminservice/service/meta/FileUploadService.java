package com.goormplay.adadminservice.service.meta;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class FileUploadService {
    @Value("${imgbb.api-key}")
    private String apiKey;

    public String uploadToImgbb(MultipartFile file) {
        try {
            // 이미지 -> Base64 인코딩
            byte[] fileBytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(fileBytes);

            // API 요청 바디 만들기
            String body = "key=" + apiKey +
                    "&image=" + java.net.URLEncoder.encode(base64Image, "UTF-8");

            // HttpClient 전송
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.imgbb.com/1/upload"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // JSON 파싱 (간단하게 response에서 image url 추출)
            String json = response.body();
            int start = json.indexOf("\"url\":\"") + 7;
            int end = json.indexOf("\"", start);
            return json.substring(start, end).replace("\\/", "/");

        } catch (Exception e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }
}
