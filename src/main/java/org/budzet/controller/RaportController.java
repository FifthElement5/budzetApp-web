package org.budzet.controller;

import org.budzet.dto.BilansDTO;
import org.budzet.service.RaportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raporty")
public class RaportController {

    @Autowired
    private RaportService raportService;

    /**
     * Endpoint zwracający pełny bilans (przychody + wydatki) dla danego miesiąca i roku.
     * Przykładowe wywołanie: /api/raporty/bilans?year=2025&month=12
     */
    @GetMapping("/bilans")
    public List<BilansDTO> getBilans(
            @RequestParam int year,
            @RequestParam int month) {
        return raportService.getPelnyBilans(year, month);
    }

    @PostMapping("/wyslij-email")
    public String wyslijEmail(@RequestParam int year, @RequestParam int month, @RequestParam String email) {
        raportService.wyslijBilansNaMaila(year, month, email);
        return "Email wysłany pomyślnie!";
    }

    // --- TUTAJ WKLEJASZ NOWĄ METODĘ ---
    @PostMapping("/wyslij-przypomnienie")
    public String wyslijPrzypomnienie(
            @RequestParam String email,
            @RequestParam String nazwa,
            @RequestParam String termin,
            @RequestParam Double kwota) {

        // Na razie wyślemy to do serwisu, żeby on zajął się mailem
        raportService.wyslijPojedynczePrzypomnienie(email, nazwa, termin, kwota);

        return "Przypomnienie wysłane!";
    }




}