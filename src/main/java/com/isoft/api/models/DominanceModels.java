package com.isoft.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DominanceModels {
    public record TypeResponse(List<TypePokemon> pokemon) {}
    public record TypePokemon(PokemonResource pokemon) {}
    public record PokemonResource(String name, String url) {}

    public record PokemonExperienceResponse(
        int id, 
        String name, 
        @JsonProperty("base_experience") int baseExperience
    ) {}

    public record TypeDominanceDTO(
        String tipo,
        @JsonProperty("total_pokemon") int totalPokemon,
        @JsonProperty("pokemon_mas_experimentado") ExperiencedPokemonDTO pokemonMasExperimentado
    ) {}

    public record ExperiencedPokemonDTO(
        int id,
        String nombre,
        @JsonProperty("base_experience") int baseExperience
    ) {}
}