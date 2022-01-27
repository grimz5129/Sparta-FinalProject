package com.sparta.projectapi.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegexService {
    public Map<String, String> parseProperties(String bodyString) {
        bodyString = bodyString.replaceAll("[^a-zA-Z0-9:,_]", "");
        String[] bits = bodyString.split(",");
        Map<String, String> values = new HashMap<>();
        for (String bit : bits) {
            values.put(bit.split(":")[0], bit.split(":")[1]);
        }
        return values;
    }
}
