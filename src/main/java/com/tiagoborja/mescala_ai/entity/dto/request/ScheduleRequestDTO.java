package com.tiagoborja.mescala_ai.entity.dto.request;

import java.time.LocalDate;
import java.util.List;

public record ScheduleRequestDTO(List<LocalDate> days,
                                 List<String> groups,
                                 List<PersonRequestDTO> people) {
}
