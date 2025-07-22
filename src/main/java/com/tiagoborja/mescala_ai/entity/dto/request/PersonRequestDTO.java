package com.tiagoborja.mescala_ai.entity.dto.request;

import java.time.LocalDate;
import java.util.List;

public record PersonRequestDTO(String name,
                               List<String> groups,
                               List<LocalDate> unavailable
) {
}
