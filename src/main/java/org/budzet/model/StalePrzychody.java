package org.budzet.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "stale_przychody")
public class StalePrzychody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_uzytkownika")
    private Integer idUzytkownika;

    @Column(name = "id_kategorii")
    private Integer idKategorii;

    @Column(name = "id_dzial")
    private Integer idDzial;

    private String nazwa;

    private BigDecimal kwota;

    private LocalDate termin;

    @Column(name = "data_wplywu")
    private LocalDate dataWplywu;

    @Column(name = "id_status")
    private Integer idStatus;

    public StalePrzychody() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdUzytkownika() { return idUzytkownika; }
    public void setIdUzytkownika(Integer idUzytkownika) { this.idUzytkownika = idUzytkownika; }

    public Integer getIdKategorii() { return idKategorii; }
    public void setIdKategorii(Integer idKategorii) { this.idKategorii = idKategorii; }

    public Integer getIdDzial() { return idDzial; }
    public void setIdDzial(Integer idDzial) { this.idDzial = idDzial; }

    public String getNazwa() { return nazwa; }
    public void setNazwa(String nazwa) { this.nazwa = nazwa; }

    public BigDecimal getKwota() { return kwota; }
    public void setKwota(BigDecimal kwota) { this.kwota = kwota; }

    public LocalDate getTermin() { return termin; }
    public void setTermin(LocalDate termin) { this.termin = termin; }

    public LocalDate getDataWplywu() { return dataWplywu; }
    public void setDataWplywu(LocalDate dataWplywu) { this.dataWplywu = dataWplywu; }

    public Integer getIdStatus() { return idStatus; }
    public void setIdStatus(Integer idStatus) { this.idStatus = idStatus; }
}