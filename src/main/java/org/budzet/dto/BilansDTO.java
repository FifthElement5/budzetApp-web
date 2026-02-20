package org.budzet.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO służące do przesyłania zbiorczych informacji o finansach (bilansie).
 * Zawiera dane zarówno o przychodach, jak i wydatkach.
 */
public class BilansDTO {
    private String nazwa;
    private BigDecimal kwota;
    private LocalDate termin;
    private String statusOpis; // Tutaj trafi nazwa z tabeli status
    private String typ;        // Tu wpiszemy "PRZYCHOD" lub "WYDATEK"

    // Konstruktor
    public BilansDTO(String nazwa, BigDecimal kwota, LocalDate termin, String statusOpis, String typ) {
        this.nazwa = nazwa;
        this.kwota = kwota;
        this.termin = termin;
        this.statusOpis = statusOpis;
        this.typ = typ;
    }

    // Gettery (potrzebne, aby Spring mógł zamienić obiekt na JSON)
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }

    public LocalDate getTermin() {
        return termin;
    }

    public void setTermin(LocalDate termin) {
        this.termin = termin;
    }

    public String getStatusOpis() {
        return statusOpis;
    }

    public void setStatusOpis(String statusOpis) {
        this.statusOpis = statusOpis;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}