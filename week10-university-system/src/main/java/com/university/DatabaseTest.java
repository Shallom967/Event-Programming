package com.university;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {

        try (Connection connection = DatabaseUtil.getConnection()) {

            System.out.println("Database connection successful!");
            System.out.println("Connected to: university_db");

        } catch (Exception e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}