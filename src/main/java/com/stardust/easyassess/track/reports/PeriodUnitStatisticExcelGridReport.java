package com.stardust.easyassess.track.reports;


import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.models.statistics.IQCHistoryGatherStatisticModel;
import com.stardust.easyassess.track.models.statistics.IQCHistoryStatisticData;
import com.stardust.easyassess.track.models.statistics.IQCHistoryStatisticSpecimen;
import com.stardust.easyassess.track.models.statistics.IQCHistoryUnitStatisticModel;
import jxl.write.Label;
import jxl.write.WriteException;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodUnitStatisticExcelGridReport extends PeriodStatisticExcelGridReport {

    private final IQCHistoryUnitStatisticModel unitModel;

    private final IQCHistoryGatherStatisticModel gatherModel;

    public PeriodUnitStatisticExcelGridReport(OutputStream outputStream, IQCHistoryUnitStatisticModel unitModel, IQCHistoryGatherStatisticModel gatherModel) throws IOException, WriteException {
        super(outputStream);
        this.unitModel = unitModel;
        this.gatherModel = gatherModel;
    }

    @Override
    public void generate() {
        selectWorksheet("汇总报表");

        try {
            IQCPlanTemplate template = unitModel.getPlan().getTemplate();
            Date startDate = unitModel.getStartDate();
            Date endDate = unitModel.getEndDate();

            // titles
            int titleStartRow = 3;
            currentWorksheet.addCell(new Label(0, titleStartRow, unitModel.getPlan().getOwner().getName(), titleFormat));
            titleStartRow++;
            currentWorksheet.addCell(new Label(0, titleStartRow, "质控品", titleFormat));
            currentWorksheet.addCell(new Label(1, titleStartRow, "检测项", titleFormat));
            currentWorksheet.addCell(new Label(2, titleStartRow, "参考值", titleFormat));
            currentWorksheet.addCell(new Label(3, titleStartRow, "实验数据", titleFormat));
            currentWorksheet.addCell(new Label(4, titleStartRow, "受控情况", titleFormat));
            currentWorksheet.addCell(new Label(5, titleStartRow, "全部实验室数据", titleFormat));
            currentWorksheet.addCell(new Label(6, titleStartRow, "总实验次数", titleFormat));
            currentWorksheet.addCell(new Label(7, titleStartRow, "总实验室数", titleFormat));

            // statistic data
            int specimenCursor = titleStartRow + 1;
            for (String specimen : unitModel.getData().getModel().keySet()) {
                IQCHistoryStatisticSpecimen unitDataSet
                        = unitModel.getData().getModel().get(specimen);

                IQCHistoryStatisticSpecimen gatherDataSet
                        = gatherModel.getData().getModel().get(specimen);

                currentWorksheet.addCell(new Label(0, specimenCursor, specimen, labelFormat));
                currentWorksheet.mergeCells(0, specimenCursor, 0, unitDataSet.getItems().keySet().size());

                int subjectCursor = specimenCursor;
                for (String subject : unitDataSet.getItems().keySet()) {
                    IQCHistoryStatisticData unitData = unitDataSet.getItems().get(subject);
                    IQCHistoryStatisticData gatherData = gatherDataSet.getItems().get(subject);
                    IQCPlanSpecimen iqcPlanSpecimen = getSpecimen(unitModel.getPlan(), subject, specimen);
                    currentWorksheet.addCell(new Label(1, subjectCursor, subject, labelFormat));
                    currentWorksheet.addCell(new Label(2, subjectCursor, getSpecimenTargetValue(iqcPlanSpecimen) + " [±" + iqcPlanSpecimen.getFloatValue() + "]", labelFormat));
                    currentWorksheet.addCell(new Label(3, subjectCursor, unitData.toString(), labelFormat));
                    currentWorksheet.addCell(new Label(4, subjectCursor, "在控:" + unitData.getInControl() + "例, 失控" + unitData.getOutOfControl() + "例", labelFormat));
                    currentWorksheet.addCell(new Label(5, subjectCursor, gatherData.toString(), labelFormat));
                    currentWorksheet.addCell(new Label(6, subjectCursor, "共" + gatherData.getCount().toString() + "次", labelFormat));
                    currentWorksheet.addCell(new Label(7, subjectCursor, "共" + gatherData.getCountOfParticipants() + "个实验室", labelFormat));
                    subjectCursor++;
                }

                specimenCursor+=unitDataSet.getItems().keySet().size();
            }

            int filterInfoStartRow = specimenCursor + 2;
            for (String name : unitModel.getFilters().keySet()) {
                currentWorksheet.addCell(new Label(0, filterInfoStartRow, name, labelFormat));
                currentWorksheet.addCell(new Label(1, filterInfoStartRow, unitModel.getFilters().get(name), labelFormat));
                filterInfoStartRow++;
            }

            // header
            int headerStartRow = 0;
            currentWorksheet.addCell(new Label(0, headerStartRow, template.getOwner().getName(), headerFormat));
            currentWorksheet.mergeCells(0, headerStartRow, 6, headerStartRow);

            currentWorksheet.addCell(new Label(0, headerStartRow + 1, template.getName(), headerFormat));
            currentWorksheet.mergeCells(0, headerStartRow + 1, 6, headerStartRow + 1);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            currentWorksheet.addCell(new Label(0, headerStartRow + 2, "统计时间范围: " + dateFormat.format(startDate) + " - " + dateFormat.format(endDate), labelFormat));
            currentWorksheet.mergeCells(0, headerStartRow + 2, 6, headerStartRow + 2);
        } catch (WriteException e) {

        }

        close();
    }
}
