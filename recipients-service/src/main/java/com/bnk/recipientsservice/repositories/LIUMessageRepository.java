package com.bnk.recipientsservice.repositories;

import com.bnk.recipientsservice.entities.ListsInfoUpdateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LIUMessageRepository extends JpaRepository<ListsInfoUpdateMessage, Long> {

}
