package org.budzet.model;

import javax.persistence.*;

@Entity
@Table(name = "dzial")
public class Dzial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nazwa;

    public Dzial() {}

    public Dzial(String nazwa) {
        this.nazwa = nazwa;
    }

    // gettery i settery
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNazwa() { return nazwa; }
    public void setNazwa(String nazwa) { this.nazwa = nazwa; }
}
