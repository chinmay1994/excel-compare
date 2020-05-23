package com.chinmay.models;

import java.util.Objects;

public class ComponentStatusModel {

//    @ExcelCellName("Component Name")
    private String componentName;

//    @ExcelCellName("Reg_Daemon")
    private String regDaemon;

//    @ExcelCellName("Component Status")
    private String componentStatus;

//    @ExcelCellName("IsStarted")
    private String isStarted;

    public ComponentStatusModel() {
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getRegDaemon() {
        return regDaemon;
    }

    public void setRegDaemon(String regDaemon) {
        this.regDaemon = regDaemon;
    }

    public String getComponentStatus() {
        return componentStatus;
    }

    public void setComponentStatus(String componentStatus) {
        this.componentStatus = componentStatus;
    }

    public String getIsStarted() {
        return isStarted;
    }

    public void setIsStarted(String isStarted) {
        this.isStarted = isStarted;
    }

    public boolean isStarted() {
        return this.isStarted.equals("1");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentStatusModel that = (ComponentStatusModel) o;
        return componentName.equals(that.componentName) &&
                regDaemon.equals(that.regDaemon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentName, regDaemon);
    }
}
