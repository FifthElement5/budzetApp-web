package org.budzet.model;

import javax.persistence.*;

@Entity
@Table(name = "uzytkownicy")
public class Uzytkownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imie;
    private String nazwisko;
    private String email;

    public Uzytkownik() {}

    public Uzytkownik(String imie, String nazwisko, String email) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
    }

    // gettery i settery
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getImie() { return imie; }
    public void setImie(String imie) { this.imie = imie; }

    public String getNazwisko() { return nazwisko; }
    public void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
