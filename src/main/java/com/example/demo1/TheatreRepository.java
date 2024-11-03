package com.example.demo1;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    @Query("SELECT p FROM Theatre p WHERE CONCAT(p.name_perfomance, '', p.troupe, '', p.dtim, '', p.total_t, '', p.free_t) LIKE %?1%")
    List<Theatre> search(@Param("name")String name);

    @Query("SELECT p FROM Theatre p ORDER BY p.dtim ")
    List<Theatre> sort();

    @Query("SELECT p.dtim, count(DISTINCT p.id) as count FROM Theatre p group by p.dtim")
    List<Objects[]> forHist();

    @Query("SELECT p.dtim, count(distinct p.id) from Theatre p group by p.dtim")
    List<Object[]> forTable();
}
