package com.smg.log.scheduler;

import com.smg.log.service.FaultReportService;
import com.smg.pojo.FaultReport;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class FaultReportScheduler {

    @Autowired
    private FaultReportService faultReportService;

    @Value("${fault.report.export.path}")
    private String exportPath;

    @Scheduled(cron = "0 32 18 * * ?") // 每天午夜执行
    public void generateDailyFaultReport() {
        LocalDate today = LocalDate.now();
        List<FaultReport> faultReports = faultReportService.getFaultReportsByDate(today);

        System.out.println("6666666"+faultReports);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Fault Reports");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Fault ID", "Worker ID", "Worker Name", "Worker Phone", "Report Time", "Description", "Status", "Severity", "Affected Systems", "Root Cause", "Resolution Steps", "Additional Notes"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 填充数据
        int rowNum = 1;
        for (FaultReport faultReport : faultReports) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(faultReport.getId());
            row.createCell(1).setCellValue(faultReport.getFaultId());
            row.createCell(2).setCellValue(faultReport.getWorkerId());
            row.createCell(3).setCellValue(faultReport.getWorkerName());
            row.createCell(4).setCellValue(faultReport.getWorkerPhone());
            LocalDateTime reportTime = faultReport.getReportTime();
            String reportTimeString = reportTime != null ? reportTime.toString() : "N/A";
            row.createCell(5).setCellValue(reportTimeString);

//            row.createCell(5).setCellValue(faultReport.getReportTime().toString());
            row.createCell(6).setCellValue(faultReport.getDescription());
            row.createCell(7).setCellValue(faultReport.getStatus());
            row.createCell(8).setCellValue(faultReport.getSeverity());
            row.createCell(9).setCellValue(faultReport.getAffectedSystems());
            row.createCell(10).setCellValue(faultReport.getRootCause());
            row.createCell(11).setCellValue(faultReport.getResolutionSteps());
            row.createCell(12).setCellValue(faultReport.getAdditionalNotes());
        }

        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 保存文件
        try (FileOutputStream fileOut = new FileOutputStream(exportPath + "fault_reports_" + today + ".xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}