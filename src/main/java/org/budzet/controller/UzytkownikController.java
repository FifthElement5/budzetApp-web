package org.budzet.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.budzet.model.Uzytkownik;
import org.budzet.service.UzytkownikService;

import java.util.List;


// kontroler REST - Spring sam serializuje zwrotki do JSON
//
@RestController
@RequestMapping("/api/uzytkownicy")
@CrossOrigin // pozwala forntowi laczyc sie z back
public class UzytkownikController {

    private final UzytkownikService service; // wstrzyknicie servisu

    public UzytkownikController(UzytkownikService service) {
        this.service = service;
    }
    //  zwraca cala liste
    @GetMapping
    public List<Uzytkownik> getAll() {
        return service.getAll();
    }
// pobiera jeden rekord - wbudowane mapowanie degmentu URL
    @GetMapping("/{id}")
    public Uzytkownik getOne(@PathVariable Integer id) {
        return service.getById(id);
    }
// POST - torzenie uzytkownika
    // @requestBody spring sam konwertuje JSON
    @PostMapping
    public Uzytkownik create(@RequestBody Uzytkownik u) {
        return service.create(u);
    }

    @PutMapping("/{id}")
    public Uzytkownik update(@PathVariable Integer id, @RequestBody Uzytkownik u) {
        return service.update(id, u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    // 🔥 NOWY POPRAWNY ENDPOINT Z PAGINACJĄ I WYSZUKIWANIEM
    @GetMapping("/search")
    public Page<Uzytkownik> search(
            @RequestParam(required = false) String nazwisko,
            @RequestParam(required = false) Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.search(nazwisko, id, pageable);
    }
}
