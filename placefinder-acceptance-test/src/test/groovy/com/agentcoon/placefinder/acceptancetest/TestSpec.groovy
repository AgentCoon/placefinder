package com.agentcoon.placefinder.acceptancetest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification

class TestSpec extends Specification {
    Logger logger = LoggerFactory.getLogger(getClass())

    @Shared
    PlaceFinderDriver placefinder = new PlaceFinderDriver()

    @Shared
    MockMapQuestService mapQuest = new MockMapQuestService()

    @Shared
    MockFacebookService facebook = new MockFacebookService()

    def setupSpec() {
        mapQuest.beforeStories()
        facebook.beforeStories()
    }

    def cleanupSpec() {
        mapQuest.afterStories()
        facebook.afterStories()
    }

    def "Find places for a unique city in a country"() {
        logger.info("TestSpec: Find places for a unique city")

        String country = "Poland"
        String city = "Poznan"
        String searchString = "burger"

        given:
        mapQuest.mockMapQuestResponse(country, city)

        and:
        facebook.mockFacebookResponseForParameters(searchString)

        expect: "One city is found"
        placefinder.aNumberOfCitiesReturned(country, city, searchString, 1)
    }

    def "Find places for all cities with the same name in one country"() {
        logger.info("TestSpec: Find places for all cities with the same name in one country")

        String country = "USA"
        String city = "Washington"
        String searchString = "burger"

        given:
        mapQuest.mockMapQuestResponse(country, city)

        and:
        facebook.mockFacebookResponseForParameters(searchString)

        expect: "Places are found in all found cities"
        placefinder.aNumberOfCitiesReturned(country, city, searchString, 3)
    }

    def "Return an error when specified location not found"() {
        logger.info("TestSpec: Error when specified location not found")

        String country = "USA"
        String city = "MadeUpCity"
        String searchString = "burger"

        given:
        mapQuest.mockMapQuestResponse(country, city)

        expect: "An error response is returned"
        placefinder.aNotFoundErrorResponse(country, city, searchString)

        and: "Facebook client is never called"
        facebook.verifyNoInteraction()
    }

    def "Return an error when mapQuest client fails"() {
        logger.info("TestSpec: Error when mapQuestClient fails")

        String country = "Poland"
        String city = "Poznan"
        String searchString = "burger"

        given:
        mapQuest.mockMapQuestFailureResponse(country, city)

        expect: "An error response is returned"
        placefinder.anErrorResponse(country, city, searchString)

        and: "Facebook client is never called"
        facebook.verifyNoInteraction()
    }
}
