package com.chinmay;

import com.chinmay.controller.StatusController;
import com.chinmay.models.Config;
import com.chinmay.util.Constants;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args)
    {
        StatusController statusController = new StatusController(args);
        statusController.process();
    }
}
