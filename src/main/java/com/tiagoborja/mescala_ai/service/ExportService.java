package com.tiagoborja.mescala_ai.service;

import com.tiagoborja.mescala_ai.entity.dto.response.ScheduleResponseDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExportService {

    public Workbook createScheduleExcel(Map<LocalDate, List<ScheduleResponseDTO>> scheduleMap) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("SCHEDULE");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Título
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("JULY");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7)); // Ajusta se necessário

        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCell.setCellStyle(titleStyle);

        // Obter grupos únicos, mantendo ordem (LinkedHashSet)
        Set<String> groups = new LinkedHashSet<>();
        for (List<ScheduleResponseDTO> schedules : scheduleMap.values()) {
            for (ScheduleResponseDTO schedule : schedules) {
                groups.add(schedule.group());
            }
        }

        // Cabeçalho
        Row headerRow = sheet.createRow(1);
        Cell cell = headerRow.createCell(0);
        cell.setCellValue("Date");
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(headerStyle);

        int colIndex = 1;
        for (String group : groups) {
            Cell groupCell = headerRow.createCell(colIndex++);
            groupCell.setCellValue(group);
            groupCell.setCellStyle(headerStyle);
        }

        // Preenchimento dos dados
        int rowIndex = 2;
        for (Map.Entry<LocalDate, List<ScheduleResponseDTO>> entry : scheduleMap.entrySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(entry.getKey().format(dateFormatter));

            // Preenche colunas de grupos (com espaços vazios por padrão)
            Map<String, ScheduleResponseDTO> groupMap = entry.getValue().stream()
                    .collect(java.util.stream.Collectors.toMap(ScheduleResponseDTO::group, s -> s));

            colIndex = 1;
            for (String group : groups) {
                Cell c = row.createCell(colIndex++);
                ScheduleResponseDTO s = groupMap.get(group);
                if (s != null) {
                    c.setCellValue(s.person());
                } else {
                    c.setCellValue("");
                }
            }
        }

        // Ajustar colunas
        for (int i = 0; i <= groups.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }
}
