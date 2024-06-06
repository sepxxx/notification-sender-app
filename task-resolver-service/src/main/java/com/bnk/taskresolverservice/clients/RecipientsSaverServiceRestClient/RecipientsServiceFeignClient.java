package com.bnk.taskresolverservice.clients.RecipientsSaverServiceRestClient;


import com.bnk.taskresolverservice.dtos.RecipientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="recipients-service")

public interface RecipientsServiceFeignClient {
    @GetMapping("/lists/recipients")
    Page<RecipientDto> getRecipientsPageByListNameAndUserId(
            @RequestParam("listName") String listName,
            @RequestHeader("sub") String userId,
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    );
}
