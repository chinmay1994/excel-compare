package com.chinmay.service;

import com.chinmay.util.Constants;

public class ServiceFactory {

    public static AbstractService getService(String type) throws IllegalAccessException {
        if(type.equals(Constants.serverFiles)) {
            return new ServerStatusService();
        } else if(type.equals(Constants.componentFiles)) {
            return new ComponentStatusService();
        } else {
            throw new IllegalAccessException("Service doesn't exist for --type "+type);
        }
    }
}
