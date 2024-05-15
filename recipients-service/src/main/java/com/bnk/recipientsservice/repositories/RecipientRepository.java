package com.bnk.recipientsservice.repositories;

import com.bnk.recipientsservice.entities.Recipient;
import com.bnk.recipientsservice.entities.RecipientList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    Page<Recipient> findAllByRecipientList(RecipientList recipientList, Pageable pageable);
}