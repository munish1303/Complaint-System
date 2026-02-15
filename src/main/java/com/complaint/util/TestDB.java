package com.complaint.util;

public class TestDB {

    public static void main(String[] args) {

        if(DBConnection.getConnection() != null)
            System.out.println("DB Connected");
        else
            System.out.println("Connection Failed");

    }
}
