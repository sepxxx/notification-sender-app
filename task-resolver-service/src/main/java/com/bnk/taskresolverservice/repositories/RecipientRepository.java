package com.bnk.taskresolverservice.repositories;
import com.bnk.taskresolverservice.entities.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

}