package org.budzet.repository;

import org.budzet.model.StaleWydatki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaportRepository extends JpaRepository<StaleWydatki, Integer> {

    @Query(value =
            // 1. Stałe wydatki
            "SELECT sw.nazwa, sw.kwota, sw.termin, s.nazwa AS status_opis, 'WYDATEK' AS typ " +
                    "FROM stale_wydatki sw " +
                    "JOIN status s ON sw.id_status = s.id " +
                    "WHERE sw.termin BETWEEN :start AND :end " +

                    "UNION ALL " +

                    // 2. Jednorazowe wydatki
                    "SELECT jw.nazwa, jw.kwota, jw.termin, s.nazwa AS status_opis, 'WYDATEK' AS typ " +
                    "FROM jednorazowe_wydatki jw " +
                    "JOIN status s ON jw.id_status = s.id " +
                    "WHERE jw.termin BETWEEN :start AND :end " +

                    "UNION ALL " +

                    // 3. Stałe przychody
                    "SELECT sp.nazwa, sp.kwota, sp.termin, s.nazwa AS status_opis, 'PRZYCHOD' AS typ " +
                    "FROM stale_przychody sp " +
                    "JOIN status s ON sp.id_status = s.id " +
                    "WHERE sp.termin BETWEEN :start AND :end " +

                    "UNION ALL " +

                    // 4. Jednorazowe przychody
                    "SELECT jp.nazwa, jp.kwota, jp.termin, s.nazwa AS status_opis, 'PRZYCHOD' AS typ " +
                    "FROM jednorazowe_przychody jp " +
                    "JOIN status s ON jp.id_status = s.id " +
                    "WHERE jp.termin BETWEEN :start AND :end " +

                    "ORDER BY termin", nativeQuery = true)
    List<Object[]> findPelnyBilans(@Param("start") String start, @Param("end") String end);
}