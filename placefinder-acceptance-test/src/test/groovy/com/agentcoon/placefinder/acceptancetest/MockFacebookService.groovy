package com.agentcoon.placefinder.acceptancetest

import com.github.tomakehurst.wiremock.WireMockServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.core.MediaType

import static com.github.tomakehurst.wiremock.client.WireMock.*

class MockFacebookService {
    private Logger logger = LoggerFactory.getLogger(getClass())

    private WireMockServer wireMockServer

    void beforeStories() {
        wireMockServer = new WireMockServer(TestParams.MOCK_FACEBOOK_SERVICE_PORT)
        wireMockServer.start()
    }

    void mockFacebookResponseForParameters(String searchString) {
        logger.info("Mocking Facebook response for searchString: {}", searchString)

        wireMockServer.stubFor(get(urlPathMatching(TestParams.MOCK_FACEBOOK_CONTEXT_PATH + "search"))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody(TestUtil.dataFileToString(searchString + ".txt"))))
    }

    void verifyNoInteraction() {
        logger.info("Verify no Facebook request was made")

        wireMockServer.verify(0, postRequestedFor(urlPathMatching(TestParams.MOCK_FACEBOOK_CONTEXT_PATH + "search")))
    }

    void afterStories() {
        wireMockServer.stop()
    }
}
