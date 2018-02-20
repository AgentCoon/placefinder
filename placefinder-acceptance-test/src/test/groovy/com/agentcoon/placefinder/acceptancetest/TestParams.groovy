package com.agentcoon.placefinder.acceptancetest

class TestParams {

    /**
     * URL to application under test
     */
    def static APP_PORT = 8380
    def static APP_BASE_URL = "http://localhost"
    def static APP_CONTEXT_PATH = "/"

    /**
     * Connection parameters for a basic simulation of MapQuest client
     */
    static MOCK_MAPQUEST_SERVICE_PORT = 8581
    static MOCK_MAPQUEST_CONTEXT_PATH = "/"
    static MOCK_MAPQUEST_APP_ID = "mapQuest_app_key"

    /**
     * Connection parameters for a basic simulation of Facebook client
     */
    static MOCK_FACEBOOK_SERVICE_PORT = 8589
    static MOCK_FACEBOOK_CONTEXT_PATH = "/"
}
