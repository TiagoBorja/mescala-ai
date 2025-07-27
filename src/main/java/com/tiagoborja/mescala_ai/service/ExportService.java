package com.tiagoborja.mescala_ai.service;

import com.tiagoborja.mescala_ai.entity.dto.response.ScheduleResponseDTO;
import com.tiagoborja.mescala_ai.export.excel.enums.ExcelCellStyle;
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

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("JULHO");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

        titleCell.setCellStyle(ExcelCellStyle.createMainHeaderStyle(workbook));

        Set<String> groups = new LinkedHashSet<>();
        for (List<ScheduleResponseDTO> schedules : scheduleMap.values()) {
            for (ScheduleResponseDTO schedule : schedules) {
                groups.add(schedule.group());
            }
        }

        Row headerRow = sheet.createRow(1);

        Cell dateHeaderCell = headerRow.createCell(0);
        dateHeaderCell.setCellValue("DATA");
        dateHeaderCell.setCellStyle(ExcelCellStyle.createColumnHeaderStyle(workbook));

        int colIndex = 1;
        for (String group : groups) {
            Cell groupCell = headerRow.createCell(colIndex++);
            groupCell.setCellValue(group);
            groupCell.setCellStyle(ExcelCellStyle.createColumnHeaderStyle(workbook));
        }

        int rowIndex = 2;
        for (Map.Entry<LocalDate, List<ScheduleResponseDTO>> entry : scheduleMap.entrySet()) {
            Row row = sheet.createRow(rowIndex++);

            Cell dateCell = row.createCell(0);
            dateCell.setCellValue(entry.getKey().format(dateFormatter));
            dateCell.setCellStyle(ExcelCellStyle.createDataCellStyle(workbook));

            Map<String, ScheduleResponseDTO> groupMap = entry.getValue().stream()
                    .collect(java.util.stream.Collectors.toMap(ScheduleResponseDTO::group, s -> s));

            colIndex = 1;
            for (String group : groups) {
                Cell cell = row.createCell(colIndex++);
                ScheduleResponseDTO schedule = groupMap.get(group);
                if (schedule != null) {
                    cell.setCellValue(schedule.person());
                    cell.setCellStyle(ExcelCellStyle.createDataCellStyle(workbook));
                } else {
                    cell.setCellStyle(ExcelCellStyle.createDataCellStyle(workbook));
                }
            }
        }

        for (int i = 0; i <= groups.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }
}