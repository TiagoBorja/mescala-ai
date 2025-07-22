package com.tiagoborja.mescala_ai.service;

import com.tiagoborja.mescala_ai.entity.Group;
import com.tiagoborja.mescala_ai.entity.Person;
import com.tiagoborja.mescala_ai.entity.Schedule;
import com.tiagoborja.mescala_ai.entity.dto.request.PersonRequestDTO;
import com.tiagoborja.mescala_ai.entity.dto.request.ScheduleRequestDTO;
import com.tiagoborja.mescala_ai.entity.dto.response.ScheduleResponseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    public Map<LocalDate, List<ScheduleResponseDTO>>generateRandomSchedule(@NotNull ScheduleRequestDTO scheduleRequestDTO) {
        Map<LocalDate, List<ScheduleResponseDTO>> responseMap = new HashMap<>();

        // Just to fill with all schedules per day
        Map<LocalDate, List<Schedule>> schedulePerDay = new HashMap<>();
        List<String> groups = scheduleRequestDTO.groups();
        List<PersonRequestDTO> people = scheduleRequestDTO.people();
        List<LocalDate> days = scheduleRequestDTO.days();

        // Verify all days that will have a schedule
        for (LocalDate day : days) {
            Set<String> assignedPeople = new HashSet<>();
            List<Schedule> schedules = new ArrayList<>();

            // Store all key/values on groupName
            for (String groupName : groups) {
                Group group = new Group(groupName);

                PersonRequestDTO selectedPerson = null;

                for (PersonRequestDTO person : people) {

                    // Ignore if the person are unavailable in this day
                    if (person.unavailable() != null && person.unavailable().contains(day)) {
                        continue;
                    }

                    // Check if person are assigned a one group
                    if (person.groups() == null || !person.groups().contains(groupName)) {
                        continue;
                    }

                    // Ignore if had a schedule
                    if (assignedPeople.contains(person.name())) {
                        continue;
                    }

                    selectedPerson = person;
                    break;
                }


                if (selectedPerson != null) {
                    Person personEntity = convertDtoToEntity(selectedPerson);
                    assignedPeople.add(selectedPerson.name());
                    schedules.add(new Schedule(group, personEntity, day));
                }
            }

            List<ScheduleResponseDTO> responseList = schedules.stream()
                    .map(s -> new ScheduleResponseDTO(
                            s.getGroup().getName(),
                            s.getPerson().getName()
                    ))
                    .toList();

            responseMap.put(day, responseList);
        }
        return responseMap;
    }

    public Person convertDtoToEntity(PersonRequestDTO dto) {
        Person p = new Person();
        p.setName(dto.name());
        p.setGroups(dto.groups().stream().map(Group::new).collect(Collectors.toList()));
        p.setUnavailable(dto.unavailable());
        return p;
    }
}
