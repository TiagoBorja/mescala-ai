package com.tiagoborja.mescala_ai.service;

import com.tiagoborja.mescala_ai.entity.Group;
import com.tiagoborja.mescala_ai.entity.Person;
import com.tiagoborja.mescala_ai.entity.Schedule;
import com.tiagoborja.mescala_ai.entity.dto.ScheduleDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    public List<Schedule> generateRandomSchedule(@NotNull ScheduleDTO scheduleDTO) {

        // Just to fill with all schedules per day
        List<Schedule> schedules = new ArrayList<>();
        Map<String, Group> groups = scheduleDTO.groups();
        List<Person> people = scheduleDTO.people();
        List<LocalDate> days = scheduleDTO.days();

        // Verify all days that will have a schedule
        for (LocalDate day : days) {
            Set<Person> assignedPeople = new HashSet<>();

            // Store all key/values on groupName
            for (String groupName : groups.keySet()) {

                Group group = groups.get(groupName);
                Person selectedPerson = null;

                for (Person person : people) {

                    // Ignore if the person are unavailable in this day
                    if (person.getUnavailable() != null && person.getUnavailable().contains(day)) {
                        continue;
                    }

                    // Check if person are assigned a one group
                    if (person.getGroups() != null && !person.getGroups()
                            .stream()
                            .map(Group::getName).toList()
                            .contains(groupName)) {
                        continue;
                    }

                    // Ignore if had a schedule
                    if (assignedPeople.contains(person)) {
                        continue;
                    }

                    selectedPerson = person;
                    break;
                }

                if (selectedPerson != null) {
                    assignedPeople.add(selectedPerson);
                    schedules.add(new Schedule(group, selectedPerson, day));
                }
            }
        }
        return schedules;
    }
}
