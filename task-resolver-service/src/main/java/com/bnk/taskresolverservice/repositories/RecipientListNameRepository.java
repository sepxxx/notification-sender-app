package com.bnk.taskresolverservice.repositories;

import com.bnk.taskresolverservice.entities.RecipientList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipientListNameRepository extends JpaRepository<RecipientList, Long> {
    Optional<RecipientList> findByNameAndUserId(String recipientListName, String userId);
//    List<RecipientList> findAllByUserId(String userId);
}