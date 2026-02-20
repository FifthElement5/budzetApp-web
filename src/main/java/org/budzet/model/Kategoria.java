package org.budzet.model;

import javax.persistence.*;

@Entity
@Table(name = "kategorie")
public class Kategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nazwa;

    public Kategoria() {}

    public Kategoria(String nazwa) {
        this.nazwa = nazwa;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNazwa() { return nazwa; }
    public void setNazwa(String nazwa) { this.nazwa = nazwa; }
}
