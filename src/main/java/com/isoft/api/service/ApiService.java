package com.isoft.api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.isoft.api.models.DominanceModels;

@Service 
public class ApiService {
    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /* 
        Metodo generalizado para que este reciba los parametros:
        - request: Ruta de la solicitud
        - responseType: Flexible a el tipo de respuesta que se espere
        Se dejo de esta manera para que pueda ser utilizado de la forma que se crea conveniente

        Metodo de uso:
            solicitudApi("pokemon/1", PokemonResponse.class)
        Como PokemonResponse usa un record con los parametros id y name
        la respuesta va a ser:
        { "id": 1, "name": "bulbasaur" }

        Para generar un record optimo es conveniente revisar la api para encontrar una solicitud 
        documentada que entregue los datos que se necesitan:
            - https://pokeapi.co/docs/v2
    */
    public <T> T solicitudApi(String request, Class<T> responseType){
        String url = "https://pokeapi.co/api/v2/" + request;
        try {
            return restTemplate.getForEntity(url, responseType).getBody();
        } catch (RestClientException e) {
            return null; 
        }
    }

    /* 
        DESAFIO 1
    */

    /* 
        DESAFIO 2
    */

    private List<Integer> obtenerIdPorTipo(DominanceModels.TypeResponse typeResponse){
        List<Integer> gen1Ids = new ArrayList<>();
        for (DominanceModels.TypePokemon tp : typeResponse.pokemon()) {
            String url = tp.pokemon().url();
            String[] parts = url.split("/");
            int id = Integer.parseInt(parts[parts.length - 1]); // obtenemos ultimo dato la id
            
            if (id >= 1 && id <= 151) {
                gen1Ids.add(id);
            }
        }
        return gen1Ids;
    }
    
    public DominanceModels.TypeDominanceDTO dominanciaPorTipo(String tipo) {
        DominanceModels.TypeResponse typeResponse = solicitudApi("type/" + tipo, DominanceModels.TypeResponse.class);
        if (typeResponse == null || typeResponse.pokemon() == null) { return null; }

        List<Integer> gen1Ids = obtenerIdPorTipo(typeResponse);
        int totalPokemonGen1 = gen1Ids.size();
        if (totalPokemonGen1 == 0) return new DominanceModels.TypeDominanceDTO(tipo, 0, null);

        List<CompletableFuture<DominanceModels.PokemonExperienceResponse>> futuros = new ArrayList<>();        
        for (Integer id : gen1Ids) {
            CompletableFuture<DominanceModels.PokemonExperienceResponse> futuro = CompletableFuture.supplyAsync(() -> 
                solicitudApi("pokemon/" + id, DominanceModels.PokemonExperienceResponse.class)
            );
            futuros.add(futuro);
        }

        DominanceModels.PokemonExperienceResponse masExperimentado = futuros.stream()
            .map(futuro -> futuro.join())
            .filter(Objects::nonNull)
            .max(Comparator.comparingInt(pokemon -> pokemon.baseExperience()))
            .orElse(null);

        DominanceModels.ExperiencedPokemonDTO expDTO = null;
        if (masExperimentado != null) {
            expDTO = new DominanceModels.ExperiencedPokemonDTO(
                masExperimentado.id(),
                masExperimentado.name(),
                masExperimentado.baseExperience()
            );
        }

        return new DominanceModels.TypeDominanceDTO(tipo, totalPokemonGen1, expDTO);
    }

    /* 
        DESAFIO 3
    */

    /* 
        DESAFIO 4
    */
}