package com.tiagoborja.mescala_ai.entity.dto;

import com.tiagoborja.mescala_ai.entity.Group;
import com.tiagoborja.mescala_ai.entity.Person;

import java.time.LocalDate;
import java.util.List;

public record ScheduleDTO(List<LocalDate> days,
                          List<Group> groups,
                          List<Person> people) {
}
