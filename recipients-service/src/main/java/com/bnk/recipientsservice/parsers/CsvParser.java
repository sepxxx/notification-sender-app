package com.bnk.recipientsservice.parsers;

import com.bnk.recipientsservice.dtos.RecipientDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CsvParser {
    List<RecipientDto> parseRecipients(MultipartFile file) throws IOException;
}
