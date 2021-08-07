package com.hqqm.training.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class AccessJournal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private String ip;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserGroup userGroup;
}
