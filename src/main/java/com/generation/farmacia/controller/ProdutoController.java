package com.generation.farmacia.controller;

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {

        return ResponseEntity.ok(produtoRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id) {

        return produtoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @GetMapping("/nome_produtos/{nome}")
    public ResponseEntity<List<Produto>> getAllByNome(@PathVariable String nome){

        return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));

    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produtoRepository.save(produto));

    }

    @PutMapping("/atualizar")
    public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {

        return produtoRepository.findById(produto.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(produtoRepository.save(produto)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        produtoRepository.deleteById(id);
    }

}
