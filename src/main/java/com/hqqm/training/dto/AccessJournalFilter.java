package com.hqqm.training.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessJournalFilter {
    private String ip;
    private Long userGroupId;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
}
