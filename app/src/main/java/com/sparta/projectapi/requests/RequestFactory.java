package com.sparta.projectapi.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class RequestFactory {

    private static ObjectMapper mapper = new ObjectMapper();
    private static String jsonFilePath = "src/test/java/com/sparta/projectapi/json/";

    private static HttpResponse<String> responseBuilder(String endpoint, Integer id, String username, String token, String requestType, String jsonEndPoint)
            throws IOException, InterruptedException, URISyntaxException {
        StringBuilder url = new StringBuilder("http://localhost");
        url.append(endpoint);
        if (id != null) {
            url.append("?id=" + id);
        }
        HttpRequest.Builder builder = HttpRequest
                .newBuilder()
                .uri(new URI(url.toString()));

        if (requestType.equals("GET")) {
            builder = builder.GET();
        } else if (requestType.equals("POST")) {
            if (jsonEndPoint != null) {
                builder = builder.POST(HttpRequest.BodyPublishers.ofFile(Path.of
                        (jsonFilePath + jsonEndPoint)));
            } else {
                builder = builder.POST(HttpRequest.BodyPublishers.ofString(""));
            }
        } else {
            builder = builder.DELETE();
        }

        HttpRequest req = builder
                .header("content-type", "application/json")
                .header("Authorization", "Basic " + username + " " + token)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(req,
                HttpResponse.BodyHandlers.ofString());

        return resp;
    }

    private static String loginMapper(HttpResponse<String> resp) throws JsonProcessingException {
        String map = mapper.readValue(resp.body(), String.class);
        return map;
    }

    public static String postLogin(String username, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/login/check", null, username, token, "POST",
                "user.json");
        return loginMapper(resp);
    }

}
