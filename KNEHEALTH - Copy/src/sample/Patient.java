package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Patient {

        String Name;
        java.sql.Date Dob;
        Integer SSN;
        Integer HeightCM;

        public Patient(String name, Integer ssn, java.sql.Date dob, int heightCM) {
            Name = name;
            Dob = dob;
            SSN = ssn;
            HeightCM = heightCM;
        }
        //Save patient to database
        public void savePTDB() {
            //put patient into the database// Establish a connection
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
                Statement statement = connection.createStatement();
                String insertString = "insert into patient(firstName, ssn, dob, heightCM) values (\'"
                        + Name + "\', "
                        + SSN + ", \'"
                        + Dob
                        + "\',"
                        + HeightCM + ");";
                statement.executeUpdate(insertString);

                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }




