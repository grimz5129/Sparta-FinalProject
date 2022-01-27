package com.sparta.projectapi.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

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

    private static HttpResponse<String> responseBuilder(String endpoint, String username,String token, String requestType, String jsonEndPoint)
            throws IOException, InterruptedException, URISyntaxException {
        StringBuilder url = new StringBuilder("http://localhost:80");
        url.append(endpoint);
//        if (id != null) {
//            url.append("?id="+id);
//        }
        HttpRequest.Builder builder = HttpRequest
                .newBuilder()
                .uri(new URI(url.toString()));

        if (requestType.equals("GET")) {
            builder = builder.GET();
        } else if (requestType.equals("POST")) {
            if (jsonEndPoint != null) {
                builder = builder.POST(HttpRequest.BodyPublishers.ofFile(Path.of
                        (jsonFilePath+jsonEndPoint)));
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

    public static String postUser() throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/user/create", null, null, "POST",
                "user.json");
        return resp.body();
    }

    public static String deleteUser(Integer id, String username, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/user/delete/" + id, username, token, "DELETE",
                null);
        return resp.body();
    }

}
