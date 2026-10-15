package com.isoft.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.isoft.api.models.WeakPokemonDTO;
import com.isoft.api.models.WeakResponse;

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

    private boolean esDebil(WeakResponse response){
        if (response.stats() != null && response.stats().size() >= 5) {
            int defense = response.stats().get(2).baseStat();
            int specialDefense = response.stats().get(4).baseStat();
            return defense < 40 && specialDefense < 40;
        }
        return false;
    }

    private List<WeakResponse> listaCrudaDebiles() {
        List<CompletableFuture<WeakResponse>> futures = new ArrayList<>();
        for(int i = 1; i <= 150; i++){
            final int pokeId = i;
            CompletableFuture<WeakResponse> future = CompletableFuture.supplyAsync(() ->
                solicitudApi("pokemon/" + pokeId, WeakResponse.class)
            );
            futures.add(future);
        }
        return futures.stream()
            .map(future -> future.join())
            .filter(Objects::nonNull)
            .filter(response -> esDebil(response)) // esto puede ser remplazado por un metodo lamda mientras deveuelva un boolean
            .collect(Collectors.toList());
    }

    public List<WeakPokemonDTO> listaDebiles(){
        List<WeakResponse> debiles = listaCrudaDebiles();
        List<WeakPokemonDTO> response = new ArrayList<>();
        
        for(WeakResponse d : debiles){
            List<String> tipos = new ArrayList<>();
            if (d.types() != null) {
                for(WeakResponse.Types t : d.types()){
                    tipos.add(t.type().name());
                }
            }
            response.add(new WeakPokemonDTO(
                d.id(),
                d.name(),
                d.stats().get(2).baseStat(),
                d.stats().get(4).baseStat(),
                tipos
            ));
        }
        return response;
    }

    /* 
        DESAFIO 2
    */

    /* 
        DESAFIO 3
    */

    /* 
        DESAFIO 4
    */
}