package sample;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;

public class BloodPressure {

        String FirstName;
        Integer SSN;
        Integer Systolic;
        Integer Diastolic;
        java.sql.Date BpDateTaken;
        String BpStage;

        //BloodPresssure constructor
        public BloodPressure(Integer ssn, Integer systolic, int diastolic, Date bpDateTaken){
            SSN = ssn;
            Systolic = systolic;
            Diastolic = diastolic;
            BpDateTaken = bpDateTaken;
        }
        //records Blood pressure to the database
        public void recordBP(){
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
                Statement statement = connection.createStatement();
                String insertString = "insert into bloodpressure(ssn, firstName, systolic, diastolic, bpDateTaken, bpStage) values ("
                        + SSN + ", '"
                        + FirstName + "', "
                        + Systolic + ", "
                        + Diastolic + ", \'"
                        + BpDateTaken + "\' , \'"
                        + BpStage + "\' );";
                int resultSet = statement.executeUpdate(insertString);
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //calculate the blood pressure stage based on systolic and diastolic readings, return string name of stage
        public String calculateStage(){
            String stage = "No Data";
            System.out.println("ENTERED CALCULATE STAGE FUNCTION");
            if(Systolic < 120 && Diastolic < 80){
                stage = "NORMAL"; }
            if(     ((119 < Systolic) && (Systolic < 130))
                    && (Diastolic < 80)){
                stage = "ELEVATED"; }
            if(  ((129 < Systolic) && (Systolic < 140))
                    || ((80 <= Diastolic) && (Diastolic < 90)) ){
                stage = "STAGE 1"; }
            if(  (139 < Systolic)  || (89 < Diastolic)){
                stage = "STAGE 2"; }
            if( (180 < Systolic) || (120 < Diastolic)) {
                stage = "HYPERTENSIVE CRISIS!";
            }
            BpStage = stage;
            return stage;
        }
        //set the bp stage
        public void setStage(String bpStage) {
            BpStage = bpStage;
        }
        //set the first name of patient
        public void setFirstName(String firstName) {
            FirstName = firstName;
        }
    }