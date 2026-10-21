package com.isoft.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isoft.api.models.DominanceModels;
import com.isoft.api.models.PokemonResponse;
import com.isoft.api.service.ApiService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("/pokemon")
public class PokeController {
    private final ApiService service;

    public PokeController(ApiService service){
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<PokemonResponse> getMethodName(@PathVariable String id) {
        PokemonResponse response = service.solicitudApi("pokemon/"+id, PokemonResponse.class);
        if (response == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return ResponseEntity.ok(response);
    }
    
    /* 
        Modificar los endpoint mas tarde a los que corespondan
    */
    @GetMapping("/weak-defense")
    public String getWeekDefense(@PathVariable String param) {
        return new String();
    }
    
    @GetMapping("/type-dominance/{type}")
    public ResponseEntity<DominanceModels.TypeDominanceDTO> getTypeDominance(@PathVariable String type) {
        DominanceModels.TypeDominanceDTO response = service.dominanciaPorTipo(type.toLowerCase());
        if (response == null) { return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/evolution-chain/{id}")
    public String getEvolutionChain(@PathVariable String param) {
        return new String();
    }

    @GetMapping("/region/{region_name}")
    public String getRegion(@PathVariable String param) {
        return new String();
    }
}
