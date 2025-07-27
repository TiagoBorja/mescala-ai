package com.tiagoborja.mescala_ai.export.excel.enums;

import org.apache.poi.ss.usermodel.*;

public enum ExcelCellStyle {
    ;

    private static final String HEADER_FONT_NAME = "Montserrat";
    private static final String DATA_FONT_NAME = "Montserrat";
    private static final short HEADER_FONT_SIZE = 11;
    private static final short DATA_FONT_SIZE = 10;

    public static CellStyle createMainHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontName(HEADER_FONT_NAME);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints(HEADER_FONT_SIZE);

        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());

        return style;
    }

    public static CellStyle createColumnHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontName(HEADER_FONT_NAME);
        font.setBold(true);
        font.setFontHeightInPoints(HEADER_FONT_SIZE);

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        return style;
    }

    public static CellStyle createDataCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontName(DATA_FONT_NAME);
        font.setBold(true);
        font.setFontHeightInPoints(DATA_FONT_SIZE);

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        return style;
    }
}