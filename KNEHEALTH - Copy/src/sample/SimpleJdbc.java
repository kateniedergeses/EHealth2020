package sample;

import java.sql.*;


public class SimpleJdbc {
    public static void main(String[] args)
            throws SQLException, ClassNotFoundException {


        // Establish a connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
        System.out.println("Database connected");

        // Create a statement
        Statement statement = connection.createStatement();

        // Execute a statement
        ResultSet resultSet = statement.executeQuery
                ("select firstName, history from patient where ssn = 555555555");

        //insert someone into Database
        ResultSet insertPt = statement.executeQuery("insert into patient (ssn, firstName, dob) values ('111111111', 'Lola', '2020-01-01');");

        // Iterate through the result and print the student names
        while (resultSet.next())
            System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getString(3));

        // Close the batabase connection
        connection.close();
    }
}