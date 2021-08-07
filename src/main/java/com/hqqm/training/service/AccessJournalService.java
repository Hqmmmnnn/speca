package com.hqqm.training.service;

import com.hqqm.training.AccessJournalSpecification;
import com.hqqm.training.dto.AccessJournalFilter;
import com.hqqm.training.entity.AccessJournal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccessJournalService {
    private final AccessJournalSpecification accessJournalSpecification;

    public Page<AccessJournal> getRegisterAccessJournal(AccessJournalFilter filter, Pageable pageable) {
        return accessJournalSpecification.findRegisterAccessJournal(filter, pageable);
    }
}
