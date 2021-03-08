package sample;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;

public class BodyComposition {

    Integer SSN;
    String Name;
    Date BCDateTaken;
    Double WeightKilo;
    Double BMI;
    String BCCategory;
    Double Height;

    public BodyComposition(Integer ssn, String name, Date dateTaken, Double weight, Double height){
        SSN = ssn;
        Name = name;
        BCDateTaken = dateTaken;
        WeightKilo = weight;
        Height = height;
    }
    //calculate and return BMI
    public double calculateBMI(){
     double bmi;
        bmi = WeightKilo / ((Height * .01) * (Height * .01));
        bmi = Math.round(bmi * 10.0) / 10.0;
        return bmi;
    }
    //calculate and return category
    public String calCategory(double bmi){
        String category;
        if(bmi < 0){
            category = "ERROR.  BMI miscalculation.  BMI below zero.";
        }else if(bmi <= 18.5){
            category = "UNDERWEIGHT";
        }else if((18.5 < bmi) && (bmi <= 24.9)){
            category = "NORMAL";
        }else if((25 <= bmi) && (bmi <= 29.9)){
            category = "OVERWEIGHT";
        }else if(30 <= bmi){
            category = "OBESE";
        }else {
            category= "ERROR.  Reenter weight";
        }
        BCCategory = category;
        return category;
    }
    //save body composition to database
    public void saveBC() {
            double bmi;
            bmi=this.calculateBMI();
            BMI = bmi;
        //put patient into the database
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate("insert into bodycomposition (ssn, firstName, bcDateTaken, weightKilo, bmi) values ("
                    + SSN + ", '"
                    + Name + "', '"
                    + BCDateTaken + "', "
                    + WeightKilo + ", "
                    + bmi + ");");

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

