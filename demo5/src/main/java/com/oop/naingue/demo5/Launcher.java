package com.oop.naingue.demo5;

import com.oop.naingue.demo5.application.loginApplication;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        try {
            System.out.println("Starting SchedMoTo Application with MongoDB...");
            System.out.println("Java Version: " + System.getProperty("java.version"));


            Application.launch(loginApplication.class, args);

        } catch (Exception e) {
            System.err.println(" Failed to launch application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}