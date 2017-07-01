package com.stardust.easyassess.track.services;


import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

public interface IQCReportingService {
    void generatePeriodicalGatherStatisticReport(String templateId,
                                                 String targetDate,
                                                 int count,
                                                 Map<String, String> filters,
                                                 OutputStream outputStream) throws Exception;
    void generatePeriodicalUnitsStatisticReport(String templateId,
                                                String targetDate,
                                                int count,
                                                Map<String, String> filters,
                                                OutputStream outputStream) throws Exception;
}
