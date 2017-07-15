package com.stardust.easyassess.track.reports;


import com.stardust.easyassess.track.models.plan.IQCPlanItem;
import com.stardust.easyassess.track.models.statistics.*;
import jxl.write.*;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class PeriodGatherStatisticExcelGridReport extends PeriodStatisticExcelGridReport {

    private final IQCHistoryGatherStatisticModel model;

    public PeriodGatherStatisticExcelGridReport(OutputStream outputStream, IQCHistoryGatherStatisticModel model) throws IOException, WriteException {
        super(outputStream);
        this.model = model;
    }

    @Override
    public void generate() {
        selectWorksheet("汇总报表");
        try {
            // header
            int headerStartRow = 0;
            currentWorksheet.addCell(new Label(0, headerStartRow, model.getTemplate().getOwner().getName(), headerFormat));
            currentWorksheet.mergeCells(0, headerStartRow, 7, headerStartRow);

            currentWorksheet.addCell(new Label(0, headerStartRow + 1, model.getTemplate().getName(), headerFormat));
            currentWorksheet.mergeCells(0, headerStartRow + 1, 7, headerStartRow + 1);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            currentWorksheet.addCell(new Label(0, headerStartRow + 2, "统计时间范围: " + dateFormat.format(model.getStartDate()) + " - " + dateFormat.format(model.getEndDate()), labelFormat));
            currentWorksheet.mergeCells(0, headerStartRow + 2, 7, headerStartRow + 2);

            // titles
            int titleStartRow = 4;
            currentWorksheet.addCell(new Label(0, titleStartRow, "质控品", titleFormat));
            currentWorksheet.addCell(new Label(1, titleStartRow, "检测项", titleFormat));
            //currentWorksheet.addCell(new Label(2, titleStartRow, "参考值", titleFormat));
            currentWorksheet.addCell(new Label(2, titleStartRow, "实验数据", titleFormat));
            currentWorksheet.addCell(new Label(3, titleStartRow, "受控情况", titleFormat));
            currentWorksheet.addCell(new Label(4, titleStartRow, "总实验次数", titleFormat));
            currentWorksheet.addCell(new Label(5, titleStartRow, "总实验室数", titleFormat));


            // statistic data
            int specimenCursor = titleStartRow + 1;
            for (String specimen : model.getData().getModel().keySet()) {
                IQCHistoryStatisticSpecimen dataSet
                        = model.getData().getModel().get(specimen);

                currentWorksheet.addCell(new Label(0, specimenCursor, specimen, labelFormat));
                currentWorksheet.mergeCells(0, specimenCursor, 0, dataSet.getItems().keySet().size());

                int subjectCursor = specimenCursor;
                for (IQCPlanItem item : model.getTemplate().getItems()) {
                    String subject = item.getSubject();
                    //for (String subject : dataSet.getItems().keySet()) {
                    IQCHistoryStatisticData data = dataSet.getItems().get(subject);
                    currentWorksheet.addCell(new Label(1, subjectCursor, subject, labelFormat));
                    //currentWorksheet.addCell(new Label(2, subjectCursor, getSpecimenTargetValue(getSpecimen(model.getTemplate(), subject, specimen)), labelFormat));
                    currentWorksheet.addCell(new Label(2, subjectCursor, data.toString(), labelFormat));
                    currentWorksheet.addCell(new Label(3, subjectCursor, "在控:" + data.getInControl() + "例, 失控" + data.getOutOfControl() + "例", labelFormat));
                    currentWorksheet.addCell(new Label(4, subjectCursor, "共" + data.getCount().toString() + "次", labelFormat));
                    currentWorksheet.addCell(new Label(5, subjectCursor, "共" + data.getCountOfParticipants() + "个实验室", labelFormat));
                    subjectCursor++;
                    //}
                }

                specimenCursor+=dataSet.getItems().keySet().size();
            }

            int filterInfoStartRow = specimenCursor + 2;
            for (String name : this.model.getFilters().keySet()) {
                currentWorksheet.addCell(new Label(0, filterInfoStartRow, name, labelFormat));
                currentWorksheet.addCell(new Label(1, filterInfoStartRow, this.model.getFilters().get(name), labelFormat));
                filterInfoStartRow++;
            }

        } catch (WriteException e) {

        }

        close();
    }
}
