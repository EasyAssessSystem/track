package com.stardust.easyassess.track.reports;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public abstract class AbstractExcelGridReport implements GridReport {

    protected WritableWorkbook workbook;

    protected WritableCellFormat headerFormat;

    protected WritableCellFormat sectionFormat;

    protected WritableCellFormat titleFormat;

    protected WritableCellFormat labelFormat;

    protected Map<String, WritableSheet> sheets;

    protected int rowIndex = 0;

    protected int columnIndex = 0;

    protected WritableSheet currentWorksheet;

    public AbstractExcelGridReport(OutputStream outputStream) throws IOException, WriteException {
        workbook = Workbook.createWorkbook(outputStream);

        headerFormat = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD));
        headerFormat.setBorder(Border.NONE, BorderLineStyle.NONE);
        headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        headerFormat.setAlignment(Alignment.CENTRE);
        headerFormat.setWrap(true);

        sectionFormat = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD));
        sectionFormat.setBorder(Border.NONE, BorderLineStyle.NONE);
        sectionFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        sectionFormat.setAlignment(Alignment.CENTRE);
        sectionFormat.setWrap(true);
        sectionFormat.setBackground(Colour.GREY_25_PERCENT);

        titleFormat = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD));
        titleFormat.setBorder(Border.NONE, BorderLineStyle.NONE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setWrap(true);

        labelFormat = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD));
        labelFormat.setBorder(Border.NONE, BorderLineStyle.NONE);
        labelFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        labelFormat.setAlignment(Alignment.LEFT);
        labelFormat.setWrap(true);

        sheets = createSheets();
    }

    protected void selectWorksheet(String sheetName) {
        if (sheets.containsKey(sheetName)) {
            currentWorksheet = sheets.get(sheetName);
            rowIndex = 0;
            columnIndex = 0;
        }
    }

    protected void close() {
        try {
            workbook.write();
            workbook.close();
        } catch (Exception e) {

        }
    }

    protected abstract Map<String, WritableSheet> createSheets();

    protected void setCursor(int row, int col) {
        this.columnIndex = col;
        this.rowIndex = row;
    }

    protected void appendColumn() {
        this.columnIndex++;
    }

    protected void appendRow() {
        this.columnIndex=0;
        this.rowIndex++;
    }

    protected void appendColumn(String content, WritableCellFormat format) throws WriteException {
        this.columnIndex++;
        currentWorksheet.addCell(new Label(this.rowIndex, this.columnIndex, content, format));
    }

    protected void appendRow(String content, WritableCellFormat format) throws WriteException {
        this.rowIndex++;
        this.columnIndex=0;
        currentWorksheet.addCell(new Label(this.rowIndex, this.columnIndex, content, format));
    }
}
