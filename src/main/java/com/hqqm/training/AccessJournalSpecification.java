package com.hqqm.training;

import com.hqqm.training.dto.AccessJournalFilter;
import com.hqqm.training.entity.AccessJournal;
import com.hqqm.training.repository.AccessJournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;

/*
  1,2021-08-08 01:45:40.000000,111.222.333.444,2020-08-08 01:45:57.000000,1
  3,2021-05-08 01:46:19.000000,333.444.555.666,2021-08-08 01:47:17.000000,2
  2,2021-07-08 01:46:04.000000,555.666.777.888,2021-08-08 01:46:14.000000,1
 */


@Component
@RequiredArgsConstructor
public class AccessJournalSpecification {

    private final AccessJournalRepository accessJournalRepository;

    public Page<AccessJournal> findRegisterAccessJournal(AccessJournalFilter filter, Pageable pageable) {
        Specification<AccessJournal> specification = ipLike(filter.getIp())
                .and(groupIdEquals(filter.getUserGroupId()))
                .and(startAndFinishDateBetween(filter.getStartDate(), filter.getFinishDate()))
                .and(fetchOrJoinEntities());

        return accessJournalRepository.findAll(specification, pageable);
    }

    private Specification<AccessJournal> fetchOrJoinEntities() {
        return (root, query, builder) -> {
            if (currentQueryIsCountRecords(query)) {
                root.join("userGroup");
            } else {
                root.fetch("userGroup");
            }

            return query.getRestriction();
        };
    }

    private Specification<AccessJournal> ipLike(String ip) {
        return (root, query, builder) -> ip != null ? builder.like(root.get("ip"), "%" + ip + "%") : null;
    }

    private Specification<AccessJournal> groupIdEquals(Long userGroupId) {
        return (root, query, builder) -> userGroupId != null ? builder.equal(root.get("userGroup").get("id"), userGroupId) : null;
    }

    private Specification<AccessJournal> startAndFinishDateBetween(LocalDateTime startDate, LocalDateTime finishDate) {
        return (root, query, builder) ->
                startDate != null && finishDate != null ? builder.between(root.get("startDate"), startDate, finishDate) : null;
    }

    private boolean currentQueryIsCountRecords(CriteriaQuery<?> criteriaQuery) {
        return criteriaQuery.getResultType() == Long.class || criteriaQuery.getResultType() == long.class;
    }
}