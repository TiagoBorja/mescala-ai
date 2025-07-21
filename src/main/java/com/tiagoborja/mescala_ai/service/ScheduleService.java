package com.tiagoborja.mescala_ai.service;

import com.tiagoborja.mescala_ai.entity.Group;
import com.tiagoborja.mescala_ai.entity.Person;
import com.tiagoborja.mescala_ai.entity.Schedule;
import com.tiagoborja.mescala_ai.entity.dto.ScheduleDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    public List<Schedule> generateRandomSchedule(ScheduleDTO scheduleDTO) {

        List<Schedule> schedules = new ArrayList<>();
        List<Group> groups = scheduleDTO.groups();
        List<Person> people = scheduleDTO.people();
        List<LocalDate> days = scheduleDTO.days();

        Schedule schedule;
        for (LocalDate day : days) {
            for (int i = 0; i < groups.size() && i < people.size(); i++) {
                schedule = new Schedule();

                Group group = groups.get(i);
                Person person = people.get(i);

                schedule.setDay(day);
                schedule.setGroup(group);
                schedule.setPerson(person);

                schedules.add(schedule);
            }
        }

        return schedules;
    }
}
