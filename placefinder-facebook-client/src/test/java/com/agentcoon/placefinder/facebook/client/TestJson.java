package com.agentcoon.placefinder.facebook.client;

import com.agentoon.placefinder.facebook.client.api.FacebookResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestJson {


    @Test
    public void test() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        String json = "{\n" +
                "   \"data\": [\n" +
                "      {\n" +
                "         \"location\": {\n" +
                "            \"city\": \"Poznan\",\n" +
                "            \"country\": \"Poland\",\n" +
                "            \"latitude\": 52.391742805905,\n" +
                "            \"longitude\": 16.858113612547,\n" +
                "            \"street\": \"Grunwaldzka 182\"\n" +
                "         },\n" +
                "         \"name\": \"Allegro\",\n" +
                "         \"id\": \"169267498107\"\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        FacebookResponseDto dto = objectMapper.readValue(json, FacebookResponseDto.class);

        assertEquals(dto.getFacebookPlaceDtos().size(), 1);
    }
}
