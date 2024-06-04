package com.bnk.taskresolverservice.dtos;

import com.bnk.taskresolverservice.entities.TaskTemplateStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskTemplateStatusRequestDto {
    Long templateId;
    TaskTemplateStatus taskTemplateStatus;
}
