package com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient;

import com.bnk.taskresolverservice.dtos.RecipientDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class RecipientSaverServiceRestClient {
    final RestTemplate restTemplate;

//    @Value(value="${recipients-service.url}")
    String recipientsServiceRootUrl = "http://localhost:8080"; //TODO: вынос + гибкость

    public Page<RecipientDto> getRecipientsPageByListNameAndUserId(String listName, String userId,
                                                                   Integer pageNumber, Integer pageSize) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(recipientsServiceRootUrl + String.format("/%s/recipients/", listName))
                .queryParam("sub", userId)
                .queryParam("pageNumber", pageNumber)
                .queryParam("pageSize", pageSize);
        ResponseEntity<CustomPageImpl<RecipientDto>> responseEntity =
                restTemplate.exchange(
                        uriComponentsBuilder.toUriString(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<CustomPageImpl<RecipientDto>>() {
                        });
        return responseEntity.getBody();
    }

}
