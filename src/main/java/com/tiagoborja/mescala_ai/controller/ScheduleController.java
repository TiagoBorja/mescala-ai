package com.tiagoborja.mescala_ai.controller;

import com.tiagoborja.mescala_ai.entity.Schedule;
import com.tiagoborja.mescala_ai.entity.dto.request.ScheduleRequestDTO;
import com.tiagoborja.mescala_ai.entity.dto.response.ScheduleResponseDTO;
import com.tiagoborja.mescala_ai.service.ScheduleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/generate-random")
    public Map<LocalDate, List<ScheduleResponseDTO>> generateRandomSchedule(@RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        return scheduleService.generateRandomSchedule(scheduleRequestDTO);
    }
}

