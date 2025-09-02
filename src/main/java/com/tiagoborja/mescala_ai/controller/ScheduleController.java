package com.tiagoborja.mescala_ai.controller;

import com.tiagoborja.mescala_ai.model.dto.request.ScheduleRequestDTO;
import com.tiagoborja.mescala_ai.model.dto.response.ScheduleResponseDTO;
import com.tiagoborja.mescala_ai.export.excel.service.ExportService;
import com.tiagoborja.mescala_ai.service.ScheduleService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ExportService exportService;

    public ScheduleController(ScheduleService scheduleService, ExportService exportService) {
        this.scheduleService = scheduleService;
        this.exportService = exportService;
    }

    @PostMapping("/export-excel")
    public ResponseEntity<byte[]> exportScheduleToExcel(@RequestBody ScheduleRequestDTO scheduleRequestDTO) throws IllegalArgumentException {

        Map<LocalDate, List<ScheduleResponseDTO>> scheduleMap = scheduleService.generateRandomSchedule(scheduleRequestDTO);

        try (Workbook workbook = exportService.createScheduleExcel(scheduleMap)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);

            byte[] bytes = baos.toByteArray();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=escala.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }
}

