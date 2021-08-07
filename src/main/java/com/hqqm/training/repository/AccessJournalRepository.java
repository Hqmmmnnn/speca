package com.hqqm.training.repository;

import com.hqqm.training.entity.AccessJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccessJournalRepository extends JpaRepository<AccessJournal, Long>, JpaSpecificationExecutor<AccessJournal> {
}
