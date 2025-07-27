package com.tiagoborja.mescala_ai.service;

import com.tiagoborja.mescala_ai.Utils.DtoUtils;
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

@Service
public class ScheduleService {

    private final Random random = new Random();

    public Map<LocalDate, List<ScheduleResponseDTO>> generateRandomSchedule(@NotNull ScheduleRequestDTO scheduleRequestDTO) {

        // Just to fill with all schedules per day
        Map<LocalDate, List<ScheduleResponseDTO>> responseMap = new HashMap<>();
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
                String lastPersonAssigned = getLastAssignedPerson(responseMap, day, groupName);

                List<PersonRequestDTO> availablePeople = people.stream()
                        .filter(p -> p.groups() != null && p.groups().contains(groupName))
                        .filter(p -> p.unavailable() == null || !p.unavailable().contains(day))
                        .filter(p -> !assignedPeople.contains(p.name()))
                        .filter(p -> lastPersonAssigned == null || !lastPersonAssigned.contains(p.name()))
                        .toList();

                PersonRequestDTO selectedPerson = null;

                if (!availablePeople.isEmpty()) {
                    selectedPerson = getRandomPerson(availablePeople);
                }

                if (selectedPerson != null) {
                    Person personEntity = DtoUtils.genericMapper(selectedPerson, Person.class);
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
        return new TreeMap<>(responseMap);
    }

    private PersonRequestDTO getRandomPerson(List<PersonRequestDTO> list) {
        return list.get(random.nextInt(list.size()));
    }

    private String getLastAssignedPerson(Map<LocalDate, List<ScheduleResponseDTO>> scheduleMap,
                                         LocalDate day,
                                         String groupName) {
        LocalDate previousDay = day.minusDays(1);

        List<ScheduleResponseDTO> previousDaySchedules = scheduleMap.get(previousDay);

        if (previousDaySchedules == null)
            return null;

        Optional<ScheduleResponseDTO> lastSchedule = previousDaySchedules.stream()
                .filter(s -> groupName.equals(s.group()))
                .findFirst();

        return lastSchedule.map(ScheduleResponseDTO::person).orElse(null);
    }
}
