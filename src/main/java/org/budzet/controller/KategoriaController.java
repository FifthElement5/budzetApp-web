package org.budzet.controller;

import org.budzet.model.Kategoria;
import org.budzet.service.KategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kategorie")
@CrossOrigin
public class KategoriaController {

    private final KategoriaService service;

    public KategoriaController(KategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Kategoria> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String nazwa
    ) {
        return service.getAll(PageRequest.of(page, size), nazwa);
    }

    @GetMapping("/{id}")
    public Kategoria getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Kategoria create(@RequestBody Kategoria kategoria) {
        return service.create(kategoria);
    }

    @PutMapping("/{id}")
    public Kategoria update(@PathVariable Integer id, @RequestBody Kategoria kategoria) {
        return service.update(id, kategoria);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
