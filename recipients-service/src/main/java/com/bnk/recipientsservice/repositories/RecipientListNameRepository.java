package com.bnk.recipientsservice.repositories;

import com.bnk.recipientsservice.entities.RecipientList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipientListNameRepository extends JpaRepository<RecipientList, Long> {
    Optional<RecipientList> findByNameAndUserId(String recipientListName, String userId);
}