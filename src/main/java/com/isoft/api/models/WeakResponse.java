package com.isoft.api.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record WeakResponse(
    int id, 
    String name,
    List<Stats> stats,
    @JsonProperty("types") 
    List<Types> types
) {
    public record Stats(
        @JsonProperty("base_stat") int baseStat, 
        Stat stat
    ) {
        public record Stat(String name) {}
    }    
    
    public record Types(
        @JsonProperty("type") 
        Type type
    ){
        public record Type(String name){}
    }
}