package com.agentcoon.placefinder.acceptancetest

import com.agentcoon.placefinder.api.PlacesDto
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.specification.RequestSpecification

import static com.jayway.restassured.RestAssured.given
import static junit.framework.Assert.assertEquals

class PlaceFinderDriver {

    RequestSpecification appSpec = new RequestSpecBuilder().setBaseUri(TestParams.APP_BASE_URL)
            .setPort(TestParams.APP_PORT).setBasePath(TestParams.APP_CONTEXT_PATH).build()

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    void aNumberOfCitiesReturned(String country, String city, String searchString, Integer expectedCityCount) {

        String json = given().spec(appSpec).expect().statusCode(200)
                .when().get("/v1/{country}/{city}/{searchString}", country, city, searchString).asString()

        List<PlacesDto> result = mapper.readValue(json, new TypeReference<List<PlacesDto>>() {})
        assertEquals(expectedCityCount, result.size())
    }

    void aNotFoundErrorResponse(String country, String city, String searchString) {

        given().spec(appSpec).expect().statusCode(404)
                .when().get("/v1/{country}/{city}/{searchString}", country, city, searchString).asString()
    }

    void anErrorResponse(String country, String city, String searchString) {

        given().spec(appSpec).expect().statusCode(500)
                .when().get("/v1/{country}/{city}/{searchString}", country, city, searchString).asString()
    }
}
