package com.isoft.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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

    /* 
        DESAFIO 3
    */

    /* 
        DESAFIO 4
    */
}