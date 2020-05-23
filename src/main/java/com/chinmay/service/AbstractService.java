package com.chinmay.service;

import java.io.FileNotFoundException;

public interface AbstractService {
    public boolean compare(String previousPath, String currentPath) throws FileNotFoundException;
}
