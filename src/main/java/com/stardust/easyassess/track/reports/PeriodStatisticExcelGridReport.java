package com.stardust.easyassess.track.reports;


import com.stardust.easyassess.track.models.plan.IQCPlanItem;
import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.models.plan.IQCSubjectSpecimenMatrix;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class PeriodStatisticExcelGridReport extends AbstractExcelGridReport {


    public PeriodStatisticExcelGridReport(OutputStream outputStream) throws IOException, WriteException {
        super(outputStream);
    }

    @Override
    protected Map<String, WritableSheet> createSheets() {
        Map<String, WritableSheet> sheetMap = new HashMap();
        sheetMap.put("汇总报表", workbook.createSheet("汇总报表", 0));
        return sheetMap;
    }

    protected String getSpecimenTargetValue(IQCPlanSpecimen specimen) {
        if (specimen.getType().equals("S")) {
            Integer targetValue = new Double(specimen.getTargetValue()).intValue();
            for (String value : specimen.getEnumValues().keySet()) {
                if (specimen.getEnumValues().get(value).equals(targetValue)) {
                    return value;
                }
            }
        } else {
            return new Double(specimen.getTargetValue()).toString();
        }

        return "";
    }

    protected IQCPlanSpecimen getSpecimen(IQCSubjectSpecimenMatrix container, String subject, String number) {
        IQCPlanItem subjectItem = null;
        for (IQCPlanItem item : container.getItems()) {
            if (item.getSubject().equals(subject)) {
                subjectItem = item;
            }
        }

        if (subjectItem != null) {
            for (IQCPlanSpecimen specimen : subjectItem.getSpecimens()) {
                if (specimen.getNumber().equals(number)) {
                    return specimen;
                }
            }
        }

        return null;
    }


    public abstract void generate();
}
