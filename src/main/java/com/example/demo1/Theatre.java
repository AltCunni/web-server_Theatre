package com.example.demo1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Entity
public class Theatre {
    private Long id;
    @Getter
    private String name_perfomance;
    @Getter
    private String troupe;
    @Getter
    private String dtim;
    @Getter
    private String total_t;
    @Getter
    private String free_t;

    protected Theatre(){
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }



    public String toString(){
        return "theatre [id"+id+"name_perfomance="+name_perfomance+"troupe="+troupe+"dtim="+dtim+"total_t="+total_t+"free_t="+free_t+"]";
    }

}

