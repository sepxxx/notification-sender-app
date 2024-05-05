package com.bnk.recipientsservice.repositories;

import com.bnk.recipientsservice.entities.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

}