package com.hqqm.training.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class AccessJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime loginDate;
    private LocalDateTime lastActionDate;
    private String ip;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserGroup userGroup;
}
