package com.generation.farmacia.controller;

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAll(){

        return ResponseEntity.ok(categoriaRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable long id){

        return categoriaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/categorias/{categoria}")
    public ResponseEntity<List<Categoria>> getAllByCategoria(@PathVariable String categoria){

        return ResponseEntity.ok(categoriaRepository.findAllByCategoriaContainingIgnoreCase(categoria));

    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));

    }

    @PutMapping("/atualizar")
    public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria){
        return categoriaRepository.findById(categoria.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(categoriaRepository.save(categoria)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if(categoria.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        categoriaRepository.deleteById(id);

    }


}
