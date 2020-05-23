package com.chinmay.util;

import com.chinmay.models.ComponentStatusModel;
import com.chinmay.models.ServerStatus;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {
    
    public static Map<String, ServerStatus> getServerStatusMap(File file) {
        Map<String, ServerStatus> map = new HashMap<>();
        Map<Integer, String> columnIndexMap = new HashMap<>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = workbook.getSheetAt(0);
            if(sheet != null) {
                createServerStatusHeaderMapping(sheet.getRow(0), columnIndexMap);
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for(int i = 1; i < physicalNumberOfRows; i++){
                    HSSFRow row = sheet.getRow(i);
                    ServerStatus status = new ServerStatus();
                    for(Map.Entry<Integer, String> entry : columnIndexMap.entrySet()) {
                        Integer columnIndex = entry.getKey();
                        String columnName = entry.getValue();
                        if(row != null) {
                            HSSFCell cell = row.getCell(columnIndex);
                            if(cell != null) {
                                switch (columnName) {
                                    case "Daemon":
                                        status.setDaemon(cell.getStringCellValue());
                                        break;
                                    case "RefMaster":
                                        status.setRefMaster(cell.getStringCellValue());
                                        break;
                                    case "Status":
                                        status.setStatus(cell.getStringCellValue());
                                        break;
                                }
                            }
                        }
                    }
                    if(status.getDaemon() != null && !status.getDaemon().isEmpty()) {
                        map.put(status.getDaemon(), status);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, ComponentStatusModel> getComponentStatusMap(File file) {
        Map<String, ComponentStatusModel> map = new HashMap<>();
        Map<Integer, String> columnIndexMap = new HashMap<>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = workbook.getSheetAt(0);
            if(sheet != null) {
                createComponentStatusHeaderMapping(sheet.getRow(0), columnIndexMap);
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for(int i = 1; i < physicalNumberOfRows; i++){
                    HSSFRow row = sheet.getRow(i);
                    ComponentStatusModel status = new ComponentStatusModel();
                    for(Map.Entry<Integer, String> entry : columnIndexMap.entrySet()) {
                        Integer columnIndex = entry.getKey();
                        String columnName = entry.getValue();
                        if(row != null) {
                            HSSFCell cell = row.getCell(columnIndex);
                            if(!cell.getCellType().equals(CellType.BLANK)) {
                                switch (columnName) {
                                    case "Component Name":
                                        status.setComponentName(cell.getStringCellValue());
                                        break;
                                    case "Reg_Daemon":
                                        status.setRegDaemon(cell.getStringCellValue());
                                        break;
                                    case "Component Status":
                                        status.setComponentStatus(cell.getStringCellValue());
                                        break;
                                    case "IsStarted":
                                        status.setIsStarted(String.valueOf(((Double)cell.getNumericCellValue()).intValue()));
                                        break;
                                }
                            }
                        }
                    }
                    if(status.getComponentName() != null && !status.getComponentName().isEmpty()) {
                        map.put(status.getComponentName(), status);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static void createServerStatusHeaderMapping(HSSFRow headerRow, Map<Integer, String> columnIndexMap) {
        for(int i = 0; i < headerRow.getLastCellNum(); i++) {
            HSSFCell cell = headerRow.getCell(i);
            if(cell != null && !cell.getCellType().equals(CellType.BLANK)) {
                String cellValue = cell.getStringCellValue();
                switch (cellValue) {
                    case "Daemon":
                    case "RefMaster":
                    case "Status":
                        columnIndexMap.put(i, cellValue);
                        break;
                }
            }
        }
    }

    private static void createComponentStatusHeaderMapping(HSSFRow headerRow, Map<Integer, String> columnIndexMap) {
        for(int i = 0; i < headerRow.getLastCellNum(); i++) {
            HSSFCell cell = headerRow.getCell(i);
            if(cell != null && !cell.getCellType().equals(CellType.BLANK)) {
                String cellValue = cell.getStringCellValue();
                switch (cellValue) {
                    case "Component Name":
                    case "Reg_Daemon":
                    case "Component Status":
                    case "IsStarted":
                        columnIndexMap.put(i, cellValue);
                        break;
                }
            }
        }
    }

}
