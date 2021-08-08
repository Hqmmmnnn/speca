package com.hqqm.training.controller;

import com.hqqm.training.dto.AccessJournalFilter;
import com.hqqm.training.entity.AccessJournal;
import com.hqqm.training.service.AccessJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AccessJournalController {
    private final AccessJournalService accessJournalService;

    @GetMapping("/registerAccess")
    public Page<AccessJournal> fetchRegisterAccessJournal(
            AccessJournalFilter filter,
            @PageableDefault
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "loginDate", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "userGroup.id", direction = Sort.Direction.DESC)
            }) Pageable pageable) {

        return accessJournalService.getRegisterAccessJournal(filter, pageable);
    }
}
