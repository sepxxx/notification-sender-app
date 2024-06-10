package com.bnk.notificationsenderservice.controllers;


import com.bnk.notificationsenderservice.services.StatsServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class StatsController {
    StatsServiceImpl statsService;
    @GetMapping("stat/task/{id}")
    public String getStatByTaskId(@PathVariable Long id) {
        return statsService.getStatByTaskId(id);
    }
}
