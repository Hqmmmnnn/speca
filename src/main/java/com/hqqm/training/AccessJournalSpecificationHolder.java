package com.hqqm.training;

import com.hqqm.training.dto.AccessJournalFilter;
import com.hqqm.training.entity.AccessJournal;
import com.hqqm.training.repository.AccessJournalRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AccessJournalSpecificationHolder {

    private final AccessJournalRepository accessJournalRepository;

    public Page<AccessJournal> findAccessJournal(@NotNull AccessJournalFilter filter, Pageable pageable) {
        Specification<AccessJournal> specification = ipLike(filter.getIp())
                .and(userGroupIdEquals(filter.getUserGroupId()))
                .and(loginDateBetween(filter.getStartDate(), filter.getFinishDate()))
                .and(fetchOrJoinAccessJournalEntities());

        return accessJournalRepository.findAll(specification, pageable);
    }

    private Specification<AccessJournal> fetchOrJoinAccessJournalEntities() {
        return (root, query, builder) -> {
            if (currentQueryIsCountRecords(query))
                root.join("userGroup");
            else
                root.fetch("userGroup");

            return query.getRestriction();
        };
    }

    private Specification<AccessJournal> ipLike(String ip) {
        return (root, query, builder) -> ip != null
                ? builder.like(root.get("ip"), "%" + ip + "%")
                : null;
    }

    private Specification<AccessJournal> userGroupIdEquals(Long userGroupId) {
        return (root, query, builder) -> userGroupId != null
                ? builder.equal(root.get("userGroup").get("id"), userGroupId)
                : null;
    }

    private Specification<AccessJournal> loginDateBetween(LocalDateTime startDate, LocalDateTime finishDate) {
        return (root, query, builder) -> (startDate != null && finishDate != null)
                ? builder.between(root.get("loginDate"), startDate, finishDate)
                : null;
    }

    private boolean currentQueryIsCountRecords(CriteriaQuery<?> criteriaQuery) {
        return criteriaQuery.getResultType() == Long.class || criteriaQuery.getResultType() == long.class;
    }
}