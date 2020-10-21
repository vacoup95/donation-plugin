package com.minecraftercity.donator;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class DonationRequester
{
    private final Donator donator;

    public DonationRequester(Donator donator)
    {
        this.donator = donator;
    }

    /**
     *
     * Returns all new donations
     *
     * @return donations
     */
    public HttpResponse<JsonNode> get()
    {
        Config config = new Config(this.donator);
        Unirest.config().defaultBaseUrl(config.baseUrl());
        return Unirest.get("donations")
                .header("pass-key", config.passKey())
                .asJson();
    }

    /**
     *
     * Returns a top 10 of donators of this month
     *
     * @return donations
     */
    public HttpResponse<JsonNode> top()
    {
        Config config = new Config(this.donator);
        Unirest.config().defaultBaseUrl(config.baseUrl());
        return Unirest.get("donations/top10").asJson();
    }

    /**
     *
     * Returns all new donations
     *
     * @return donations
     * @param id
     */
    public HttpResponse<JsonNode> patch(String id)
    {
        Config config = new Config(this.donator);
        return Unirest.jsonPatch(config.baseUrl() + "donations/" + id)
                .header("pass-key", config.passKey())
                .asJson();
    }
}
