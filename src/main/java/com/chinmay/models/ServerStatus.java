package com.chinmay.models;

import java.util.Objects;

public class ServerStatus {

//    @ExcelCellName("Daemon")
    private String daemon;

//    @ExcelCellName("RefMaster")
    private String refMaster;

//    @ExcelCellName("Status")
    private String status;

    public ServerStatus() {
    }

    public String getDaemon() {
        return daemon;
    }

    public void setDaemon(String daemon) {
        this.daemon = daemon;
    }

    public String getRefMaster() {
        return refMaster;
    }

    public void setRefMaster(String refMaster) {
        this.refMaster = refMaster;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerStatus status1 = (ServerStatus) o;
        return daemon.equals(status1.daemon) &&
                refMaster.equals(status1.refMaster) &&
                status.equals(status1.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(daemon, refMaster, status);
    }
}
