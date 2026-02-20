package org.budzet.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WydatekRaportDTO {
    private String nazwa;
    private BigDecimal kwota;
    private LocalDate termin;
    private LocalDate dataZaplaty;
    private String statusOpis;

    // Konstruktor jest ważny dla Springa przy mapowaniu wyników
    public WydatekRaportDTO(String nazwa, BigDecimal kwota, LocalDate termin, LocalDate dataZaplaty, String statusOpis) {
        this.nazwa = nazwa;
        this.kwota = kwota;
        this.termin = termin;
        this.dataZaplaty = dataZaplaty;
        this.statusOpis = statusOpis;
    }

    // Gettery i Settery
    public String getNazwa() { return nazwa; }
    public void setNazwa(String nazwa) { this.nazwa = nazwa; }

    public BigDecimal getKwota() { return kwota; }
    public void setKwota(BigDecimal kwota) { this.kwota = kwota; }

    public LocalDate getTermin() { return termin; }
    public void setTermin(LocalDate termin) { this.termin = termin; }

    public LocalDate getDataZaplaty() { return dataZaplaty; }
    public void setDataZaplaty(LocalDate dataZaplaty) { this.dataZaplaty = dataZaplaty; }

    public String getStatusOpis() { return statusOpis; }
    public void setStatusOpis(String statusOpis) { this.statusOpis = statusOpis; }
}