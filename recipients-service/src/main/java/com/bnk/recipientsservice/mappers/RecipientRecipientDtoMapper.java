package com.bnk.recipientsservice.mappers;

import com.bnk.recipientsservice.dtos.RecipientDto;
import com.bnk.recipientsservice.entities.Recipient;
import com.bnk.recipientsservice.entities.RecipientList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipientRecipientDtoMapper {

//    @Mapping(target = "recipientList", source = "recipientList")
//    Recipient recipientDtoToRecipient(RecipientDto recipientDto,  RecipientList recipientList);
    Recipient recipientDtoToRecipient(RecipientDto recipientDto);
    RecipientDto recipientToRecipientDto(Recipient recipient);
}
