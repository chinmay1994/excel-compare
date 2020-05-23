package com.chinmay.service;

import com.chinmay.models.ComponentStatus;
import com.chinmay.models.ComponentStatusModel;
import com.chinmay.util.ExcelUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComponentStatusService implements AbstractService {
    private static final String format = "%s,%s,%s,%s,%s,%s,%s,%s\n";
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
        Map<String, ComponentStatusModel> previousMap = ExcelUtils.getComponentStatusMap(previousFile);
        Map<String, ComponentStatusModel> currentMap = ExcelUtils.getComponentStatusMap(currentFile);
        System.out.println("Rows in previous excel: " + previousMap.size());
        System.out.println("Rows in current excel: " + currentMap.size());
        boolean result = compareStatus(previousMap, currentMap);
        System.out.println("Finished comparing status, files same: "+result);
        return false;
    }

    public boolean compareStatus(Map<String, ComponentStatusModel> previousMap, Map<String, ComponentStatusModel> currentMap) {
        List<String> result = new ArrayList<>();
        for(Map.Entry<String, ComponentStatusModel> entry: previousMap.entrySet()) {
            String key = entry.getKey();
            ComponentStatusModel previousStatus = entry.getValue();
            ComponentStatusModel currentStatus = currentMap.get(key);
            if(currentStatus == null) {
                String serverStatusError = String.format(format,
                        previousStatus.getComponentName(), "",
                        previousStatus.getRegDaemon(), "",
                        ComponentStatus.getStatus(previousStatus.getComponentStatus()), "",
                        previousStatus.getIsStarted(), "");
                result.add(serverStatusError);
            } else {
                boolean status = compareComponentStatus(previousStatus, currentStatus);
                String serverStatusError = String.format(format,
                        previousStatus.getComponentName(), currentStatus.getComponentName(),
                        previousStatus.getRegDaemon(), currentStatus.getRegDaemon(),
                        ComponentStatus.getStatus(previousStatus.getComponentStatus()), ComponentStatus.getStatus(currentStatus.getComponentStatus()),
                        previousStatus.getIsStarted(), currentStatus.getIsStarted());
                if(!status || !currentStatus.equals(previousStatus)) {
                    result.add(serverStatusError);
                }
            }
        }
        for(Map.Entry<String, ComponentStatusModel> entry: currentMap.entrySet()) {
            String key = entry.getKey();
            ComponentStatusModel currentStatus = entry.getValue();
            ComponentStatusModel previousStatus = previousMap.get(key);
            if(previousStatus == null) {
                String serverStatusError = String.format(format,
                        "", currentStatus.getComponentName(),
                        "", currentStatus.getRegDaemon(),
                        "", ComponentStatus.getStatus(currentStatus.getComponentStatus()),
                        "", currentStatus.getIsStarted());
                result.add(serverStatusError);
            }
        }
        writeResult(result);
        return result.isEmpty();
    }

    private boolean compareComponentStatus(ComponentStatusModel previous, ComponentStatusModel current) {
        ComponentStatus previousStatus = ComponentStatus.getStatus(previous.getComponentStatus());
        ComponentStatus currentStatus = ComponentStatus.getStatus(current.getComponentStatus());
        boolean previousIsStarted = previous.isStarted();
        boolean currentStartedIsStarted = current.isStarted();
        if(previousStatus.equals(currentStatus)) {
            return true;
        } else {
            if(previousStatus.equals(ComponentStatus.RUN) || previousStatus.equals(ComponentStatus.WAIT)) {
                if (!currentStatus.equals(ComponentStatus.WAIT)) {
                    return currentStatus.equals(ComponentStatus.OFF) && currentStartedIsStarted;
                }
                return true;
            }
            if(previousStatus.equals(ComponentStatus.OFF)) {
                return currentStatus.equals(ComponentStatus.RUN);
            }
            if(previousStatus.equals(ComponentStatus.OFF_ERROR)) {
                if(currentStatus.equals(ComponentStatus.RUN) || currentStatus.equals(ComponentStatus.OFF)) {
                    return currentStartedIsStarted;
                }
                return !currentStatus.equals(ComponentStatus.OFF_ERROR_DISABLED);
            }
            if(previousStatus.equals(ComponentStatus.MANUAL) || currentStatus.equals(ComponentStatus.MANUAL)) {
                return false;
            }
            return false;
        }
    }

    private void writeResult(List<String> result) {
        if(!result.isEmpty()) {
            try(FileWriter writer = new FileWriter("component-status-comparison.csv")) {
                writer.write(String.format(format, "Previous Component Name", "Current Component Name", "Previous Reg_Daemon", "Current Reg_Daemon", "Previous Component Status", "Current Component Status", "Previous IsStarted", "Current IsStarted"));
                for(String error: result) {
                    writer.write(error);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No difference in the files!");
        }
    }
}
