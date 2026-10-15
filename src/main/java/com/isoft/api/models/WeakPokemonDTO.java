package com.isoft.api.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder ({"id", "name", "defense", "sp_defense", "tipos"})
public record WeakPokemonDTO(
    int id, 
    String name, 
    int defense,
    @JsonProperty("sp_defense") int spDefense,
    List<String> tipos
) {}
