package com.bookdream.sbb.trade;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    
    private String title;
    private int price;
    private String writer;
    private String explain;
    private LocalDateTime postdate;
    private String id;

}