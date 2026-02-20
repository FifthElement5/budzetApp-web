package org.budzet.service;

import org.budzet.dto.BilansDTO;
import org.budzet.repository.RaportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaportService {

    @Autowired
    private RaportRepository raportRepository;

    public List<BilansDTO> getPelnyBilans(int year, int month) {
        // Obliczamy zakres dat dla wybranego miesiąca
        YearMonth yearMonth = YearMonth.of(year, month);
        String start = yearMonth.atDay(1).toString();
        String end = yearMonth.atEndOfMonth().toString();

        // Pobieramy dane z bazy (lista tablic obiektów)
        List<Object[]> results = raportRepository.findPelnyBilans(start, end);

        // Mapujemy Object[] na BilansDTO
        return results.stream().map(record -> new BilansDTO(
                (String) record[0],                          // nazwa
                (BigDecimal) record[1],                      // kwota
                ((Date) record[2]).toLocalDate(),            // termin
                (String) record[3],                          // status_opis (to jest nazwa statusu!)
                (String) record[4]                           // typ (WYDATEK/PRZYCHOD)
        )).collect(Collectors.toList());
    }

    @Autowired
    private EmailService emailService;

    public void wyslijBilansNaMaila(int year, int month, String emailOdbiorcy) {
        // 1. Pobieramy dane te same, które idą do tabelki na stronie
        List<BilansDTO> dane = getPelnyBilans(year, month);

        // 2. Budujemy treść maila (StringBuilder jest wydajniejszy)
        StringBuilder sb = new StringBuilder();
        sb.append("Twoje zestawienie finansowe za: ").append(month).append("/").append(year).append("\n\n");
        sb.append("-------------------------------------------------------------------------\n");
        sb.append(String.format("%-30s | %-30s | %-30s\n", "Nazwa", "Kwota", "Typ"));
        sb.append("-------------------------------------------------------------------------\n");

        double sumaPrzychodow = 0;
        double sumaWydatkow = 0;

        for (BilansDTO operacja : dane) {
            sb.append(String.format("%-30s | %-30.2f | %-30s\n",
                    operacja.getNazwa(), operacja.getKwota(), operacja.getTyp()));

            if ("PRZYCHOD".equals(operacja.getTyp())) {
                sumaPrzychodow += operacja.getKwota().doubleValue();
            } else {
                sumaWydatkow += operacja.getKwota().doubleValue();
            }
        }

        sb.append("-------------------------------------------------------------------------\n");
        sb.append("SUMA PRZYCHODÓW: ").append(sumaPrzychodow).append(" zł\n");
        sb.append("SUMA WYDATKÓW:   ").append(sumaWydatkow).append(" zł\n");
        sb.append("BILANS KOŃCOWY:  ").append(sumaPrzychodow - sumaWydatkow).append(" zł\n");


        // 3. Wysyłamy!
        emailService.wyslijProstyEmail(emailOdbiorcy, "Raport Finansowy " + month + "/" + year, sb.toString());
    }




    public void wyslijPojedynczePrzypomnienie(String email, String nazwa, String termin, Double kwota) {
        String tresc = "Przypominamy o płatności za: " + nazwa +
                "\nTermin: " + termin +
                "\nKwota: " + kwota + " zł";

        // Tu użyj swojej metody do wysyłania maili (tej samej, której używasz w wyslijBilansNaMaila)
        System.out.println("Wysyłam maila do: " + email + " z treścią: " + tresc);
        emailService.wyslijProstyEmail( email ,"Przypomnienie terminu opłaty: " + termin , tresc);
    }


}