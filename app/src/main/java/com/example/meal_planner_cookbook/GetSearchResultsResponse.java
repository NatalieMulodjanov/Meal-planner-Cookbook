package com.example.meal_planner_cookbook;

import java.util.List;

public class GetSearchResultsResponse implements ApiResults{
    private List<Result> results;
    private int totalResults;
    private static String baseUri = "https://spoonacular.com/recipeImages/";

    public GetSearchResultsResponse(List<Result> results, int totalResults) {
        this.results = results;
        this.totalResults = totalResults;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return this.results.size();
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public static String getBaseUri() {
        return baseUri;
    }

    public static void setBaseUri(String baseUri) {
        GetSearchResultsResponse.baseUri = baseUri;
    }
}
