package com.agentcoon.placefinder.acceptancetest

import com.github.tomakehurst.wiremock.WireMockServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.core.MediaType

import static com.github.tomakehurst.wiremock.client.WireMock.*

class MockMapQuestService {
    private Logger logger = LoggerFactory.getLogger(getClass())

    private WireMockServer wireMockServer

    void beforeStories() {
        wireMockServer = new WireMockServer(TestParams.MOCK_MAPQUEST_SERVICE_PORT)
        wireMockServer.start()
    }

    void mockMapQuestResponse(String country, String city) {
        logger.info("Mocking MapQuest response for country: {}, city: {}", country, city)

        wireMockServer.stubFor(get(urlPathMatching(TestParams.MOCK_MAPQUEST_CONTEXT_PATH + "search"))
                .withQueryParam("key", equalTo(TestParams.MOCK_MAPQUEST_APP_ID))
                .withQueryParam("country", equalTo(country))
                .withQueryParam("city", equalTo(city))
                .withQueryParam("format", equalTo("json"))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody(TestUtil.dataFileToString(city + ".txt"))))
    }

    void mockMapQuestNotFoundResponse(String country, String city) {
        logger.info("Mocking MapQuest not found response for country: {}, city: {}", country, city)

        wireMockServer.stubFor(get(urlPathMatching(TestParams.MOCK_MAPQUEST_CONTEXT_PATH + "search"))
                .withQueryParam("key", equalTo(TestParams.MOCK_MAPQUEST_APP_ID))
                .withQueryParam("country", equalTo(country))
                .withQueryParam("city", equalTo(city))
                .withQueryParam("format", equalTo("json"))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody("[]")))
    }

    void mockMapQuestFailureResponse(String country, String city) {
        logger.info("Mocking MapQuest failure response for country: {}, city: {}", country, city)

        wireMockServer.stubFor(get(urlPathMatching(TestParams.MOCK_MAPQUEST_CONTEXT_PATH + "search"))
                .withQueryParam("key", equalTo(TestParams.MOCK_MAPQUEST_APP_ID))
                .withQueryParam("country", equalTo(country))
                .withQueryParam("city", equalTo(city))
                .withQueryParam("format", equalTo("json"))
                .willReturn(aResponse()
                .withStatus(500)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)))
    }

    void afterStories() {
        wireMockServer.stop()
    }
}
