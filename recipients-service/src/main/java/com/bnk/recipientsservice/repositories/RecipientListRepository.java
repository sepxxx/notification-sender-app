package com.bnk.recipientsservice.repositories;

import com.bnk.recipientsservice.entities.RecipientList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipientListRepository extends JpaRepository<RecipientList, Long> {
    Optional<RecipientList> findByNameAndUserId(String recipientListName, String userId);
    List<RecipientList> findAllByUserId(String userId);

    Boolean existsByNameAndUserId(String name, String userId);

    void deleteByNameAndUserId(String recipientListName, String userId);
}