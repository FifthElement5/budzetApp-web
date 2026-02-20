package org.budzet.model;

import javax.persistence.*;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nazwa;

    public Status() {}

    public Status(String nazwa) {
        this.nazwa = nazwa;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNazwa() { return nazwa; }
    public void setNazwa(String nazwa) { this.nazwa = nazwa; }
}
