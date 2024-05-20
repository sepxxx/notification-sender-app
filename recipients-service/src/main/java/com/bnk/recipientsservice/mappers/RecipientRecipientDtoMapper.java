package com.bnk.recipientsservice.mappers;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.entities.Recipient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipientRecipientDtoMapper {
    Recipient recipientDtoToRecipient(RecipientDto recipientDto);
    RecipientDto recipientToRecipientDto(Recipient recipient);
}
