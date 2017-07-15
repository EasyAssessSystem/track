package com.stardust.easyassess.track.reports;


import com.stardust.easyassess.track.models.plan.IQCPlanItem;
import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.models.statistics.IQCHistoryStatisticData;
import com.stardust.easyassess.track.models.statistics.IQCHistoryStatisticSpecimen;
import com.stardust.easyassess.track.models.statistics.IQCHistoryUnitStatisticModel;
import jxl.write.Label;
import jxl.write.WriteException;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PeriodUnitsStatisticExcelGridReport extends PeriodStatisticExcelGridReport {

    private final Map<String, IQCHistoryUnitStatisticModel> model;

    public PeriodUnitsStatisticExcelGridReport(OutputStream outputStream, Map<String, IQCHistoryUnitStatisticModel> model) throws IOException, WriteException {
        super(outputStream);
        this.model = model;
    }

    @Override
    public void generate() {
        selectWorksheet("汇总报表");

        try {
            int offset = 0;
            IQCPlanTemplate template = null;
            Date startDate = null;
            Date endDate = null;
            for (String unit : model.keySet()) {
                if (template == null) {
                    template = model.get(unit).getPlan().getTemplate();
                    startDate = model.get(unit).getStartDate();
                    endDate = model.get(unit).getEndDate();
                }

                // titles
                int titleStartRow = offset + 3;
                currentWorksheet.addCell(new Label(0, titleStartRow, model.get(unit).getPlan().getOwner().getName(), titleFormat));
                titleStartRow++;
                currentWorksheet.addCell(new Label(0, titleStartRow, "质控品", titleFormat));
                currentWorksheet.addCell(new Label(1, titleStartRow, "检测项", titleFormat));
                currentWorksheet.addCell(new Label(2, titleStartRow, "参考值", titleFormat));
                currentWorksheet.addCell(new Label(3, titleStartRow, "实验数据", titleFormat));
                currentWorksheet.addCell(new Label(4, titleStartRow, "受控情况", titleFormat));
                currentWorksheet.addCell(new Label(5, titleStartRow, "总实验次数", titleFormat));

                // statistic data
                int specimenCursor = titleStartRow + 1;
                for (String specimen : model.get(unit).getData().getModel().keySet()) {
                    IQCHistoryStatisticSpecimen dataSet
                            = model.get(unit).getData().getModel().get(specimen);

                    currentWorksheet.addCell(new Label(0, specimenCursor, specimen, labelFormat));
                    currentWorksheet.mergeCells(0, specimenCursor, 0, dataSet.getItems().keySet().size());

                    int subjectCursor = specimenCursor;
                    for (IQCPlanItem item : template.getItems()) {
                        String subject = item.getSubject();
                        //for (String subject : dataSet.getItems().keySet()) {
                            IQCHistoryStatisticData data = dataSet.getItems().get(subject);
                            IQCPlanSpecimen iqcPlanSpecimen = getSpecimen(model.get(unit).getPlan(), subject, specimen);
                            currentWorksheet.addCell(new Label(1, subjectCursor, subject, labelFormat));
                            currentWorksheet.addCell(new Label(2, subjectCursor, getSpecimenTargetValue(iqcPlanSpecimen) + " [±" + iqcPlanSpecimen.getFloatValue() + "]", labelFormat));
                            currentWorksheet.addCell(new Label(3, subjectCursor, data.toString(), labelFormat));
                            currentWorksheet.addCell(new Label(4, subjectCursor, "在控:" + data.getInControl() + "例, 失控" + data.getOutOfControl() + "例", labelFormat));
                            currentWorksheet.addCell(new Label(5, subjectCursor, "共" + data.getCount().toString() + "次", labelFormat));
                            subjectCursor++;
                        //}
                    }


                    specimenCursor+=dataSet.getItems().keySet().size();
                }

                int filterInfoStartRow = specimenCursor + 2;
                for (String name : this.model.get(unit).getFilters().keySet()) {
                    currentWorksheet.addCell(new Label(0, filterInfoStartRow, name, labelFormat));
                    currentWorksheet.addCell(new Label(1, filterInfoStartRow, this.model.get(unit).getFilters().get(name), labelFormat));
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

                offset = filterInfoStartRow + 1;
            }
        } catch (WriteException e) {

        }

        close();
    }
}
