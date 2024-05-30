package com.bnk.taskresolverservice.mappers;


import com.bnk.taskresolverservice.dtos.RecipientDto;
import com.bnk.taskresolverservice.entities.Recipient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipientRecipientDtoMapper {

//    @Mapping(target = "recipientList", source = "recipientList")
//    Recipient recipientDtoToRecipient(RecipientDto recipientDto,  RecipientList recipientList);
    Recipient recipientDtoToRecipient(RecipientDto recipientDto);
    RecipientDto recipientToRecipientDto(Recipient recipient);
}
