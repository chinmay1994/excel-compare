package com.chinmay.service;

import com.chinmay.models.ServerStatus;
import com.chinmay.util.ExcelUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerStatusService implements AbstractService {

    @Override
    public boolean compare(String previousPath, String currentPath) throws FileNotFoundException {
        System.out.println("Comparing server status files..");
        File previousFile = new File(previousPath);
        if(!previousFile.exists()) {
            throw new FileNotFoundException("The file "+previousPath+ " doesn't exist, please check the path");
        }
        File currentFile = new File(currentPath);
        if(!currentFile.exists()) {
            throw new FileNotFoundException("The file "+currentPath+ " doesn't exist, please check the path");
        }
        Map<String, ServerStatus> previousMap = ExcelUtils.getServerStatusMap(previousFile);
        Map<String, ServerStatus> currentMap = ExcelUtils.getServerStatusMap(currentFile);
        System.out.println("Rows in previous excel: "+previousMap.size());
        System.out.println("Rows in current excel: "+currentMap.size());
        boolean result = compareStatus(previousMap, currentMap);
        System.out.println("Finished comparing status, files same: "+result);
        return false;
    }

    public boolean compareStatus(Map<String, ServerStatus> previousMap, Map<String, ServerStatus> currentMap) {
        List<String> result = new ArrayList<>();
        String format = "%s,%s,%s,%s,%s,%s\n";
        for(Map.Entry<String, ServerStatus> entry: previousMap.entrySet()) {
            String key = entry.getKey();
            ServerStatus previousStatus = entry.getValue();
            ServerStatus currentStatus = currentMap.get(key);
            if(currentStatus == null) {
                String serverStatusError = String.format(format,
                        previousStatus.getDaemon(), "",
                        previousStatus.getRefMaster(), "",
                        previousStatus.getStatus(), "");
                result.add(serverStatusError);
            } else {
                if(!currentStatus.equals(previousStatus)) {
                    String serverStatusError = String.format(format,
                            previousStatus.getDaemon(), currentStatus.getDaemon(),
                            previousStatus.getRefMaster(), currentStatus.getRefMaster(),
                            previousStatus.getStatus(), currentStatus.getStatus());
                    result.add(serverStatusError);
                }
            }
        }
        for(Map.Entry<String, ServerStatus> entry: currentMap.entrySet()) {
            String key = entry.getKey();
            ServerStatus currentStatus = entry.getValue();
            ServerStatus previousStatus = previousMap.get(key);
            if(previousStatus == null) {
                String serverStatusError = String.format(format,
                        "", currentStatus.getDaemon(),
                        "", currentStatus.getRefMaster(),
                        "", currentStatus.getStatus());
                result.add(serverStatusError);
            }
        }
        if(!result.isEmpty()) {
            try(FileWriter writer = new FileWriter("server-status-comparison.csv")) {
                writer.write(String.format(format, "Previous Daemon", "Current Daemon", "PreviousRefMaster", "CurrentRefMaster", "PreviousStatus", "CurrentStatus"));
                for(String error: result) {
                    writer.write(error);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No difference in the files!");
        }
        return result.isEmpty();
    }
}
