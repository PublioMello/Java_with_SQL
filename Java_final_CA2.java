/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package java_final_ca2;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Java_final_CA2 {

    // CODE MADE BY PUBLIO FILHO FOR CCT COLLEGE
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        
        
        // code to connect to the database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HouseRenting", "root", "Qualquersenha1!");
            System.out.println("\n We are successfully connected to the HouseRenting database \n");
            
            //After conneced with the DB 
            // Creating variable
            int chooseNumber = 0;
            
            do {// loop to ask them to write the value between 1 and 8
                try {// To see if its a integer
                    //The menu to choose the number
                    System.out.print(" Enter a corresponding number to the options bellow: "
                            + "\n 1 – Retrieve all clients along with their associated properties."
                            + "\n 2 – Calculate the total monthly rent for each client. "
                            + "\n 3 – Count the total number of properties owned by each owner. "
                            + "\n 4 – Identify Oweners who own multiple properties."
                            + "\n 5 – List all clients along with the total rent they pay annually, sorted by the choosing order."
                            + "\n 6 – Find the client who pays the highest monthly rent."
                            + "\n 7 – Choose a letter to list all properties rented out by all clients whose name begins with it."
                            + "\n 8 – Quit the program. \n"); 
                    System.out.println("Type your option: ");
                    chooseNumber = myScanner.nextInt();
                    
                    // to give the answer that he choose
                    switch (chooseNumber) {
                        case 1:
                            //start the code for number 1
                            // Create a Statement object to execute queries
                            Statement statement_one = con.createStatement();
                            
                            // SQL query to select all clients along with their associated properties
                            String query1 = "SELECT c.client_num, c.client_name, p.property_no, p.property_address " +
                                    "FROM Client c " +
                                    "JOIN Rentals r ON c.client_num = r.client_num " +
                                    "JOIN Properties p ON r.property_no = p.property_no";
                            // Execute the query and get the ResultSet
                            ResultSet queryResult1 = statement_one.executeQuery(query1);
                            
                            // Print table data
                            System.out.println("\nList of all clients and their related properties:\n");
                            while (queryResult1.next()) {
                                System.out.println("Client Number: " + queryResult1.getString("client_num"));
                                System.out.println("Client Name: " + queryResult1.getString("client_name"));
                                System.out.println("Property Number: " + queryResult1.getString("property_no"));
                                System.out.println("Property Address: " + queryResult1.getString("property_address"));
                                System.out.println("--------------------");
                            }
                            System.out.println("\n");
                            
                            // Close the ResultSet and Statement
                            queryResult1.close();
                            statement_one.close();
                            
                            break;//End the code for number 1

                        case 2:
                            //starting the query for number 2
                            // Create a Statement object to execute queries
                            Statement statement_two = con.createStatement();
                            
                            // SQL query to calculate the total monthly rent for each client
                            String query2 = "SELECT c.client_num, c.client_name, SUM(p.monthly_rent) AS total_monthly_rent " +
                                            "FROM Client c " +
                                            "JOIN Rentals r ON c.client_num = r.client_num " +
                                            "JOIN Properties p ON r.property_no = p.property_no " +
                                            "GROUP BY c.client_num, c.client_name";
                            
                            // Execute the query and get the ResultSet
                            ResultSet queryResult2 = statement_two.executeQuery(query2);
                            
                            // Print table data
                            System.out.println("\nTotal monthly rent for each client:\n");
                            while (queryResult2.next()) {
                                System.out.println("Client Number: " + queryResult2.getString("client_num"));
                                System.out.println("Client Name: " + queryResult2.getString("client_name"));
                                System.out.println("Total Monthly Rent: " + queryResult2.getDouble("total_monthly_rent"));
                                System.out.println("--------------------");
                            }
                            System.out.println("\n");
                            
                             // Close the ResultSet and Statement
                            queryResult2.close();
                            statement_two.close();
                             
                            break;// END OF THE CODE FOR NUMBER 2

                        case 3:
                            //START OF THE CODE FOR NUMBER 3
                            // Create a Statement object to execute queries
                            Statement statement_three = con.createStatement();
                            
                            // SQL query to count the total number of properties owned by each owner
                            String query3 = "SELECT o.owner_no, o.owner_name, COUNT(*) AS total_properties_owned " +
                                            "FROM Owner o " +
                                            "JOIN Properties p ON o.owner_no = p.owner_no " +
                                            "GROUP BY o.owner_no, o.owner_name";
                            
                            // Execute the query and get the ResultSet
                            ResultSet queryResult3 = statement_three.executeQuery(query3);
                            
                            // Print table data
                            System.out.println("\nTotal number of properties owned by each owner:\n");
                            while (queryResult3.next()) {
                                System.out.println("Owner Number: " + queryResult3.getString("owner_no"));
                                System.out.println("Owner Name: " + queryResult3.getString("owner_name"));
                                System.out.println("Total Properties Owned: " + queryResult3.getInt("total_properties_owned"));
                                System.out.println("--------------------");
                            }
                            System.out.println("\n");
                            
                            // Close the ResultSet and Statement
                            queryResult3.close();
                            statement_three.close();
                            
                            
                            break; //END OF THE CODE FOR NUMBER 3

                        case 4:
                            //START THE CODE FOR NUMBER 4
                            // Create a Statement object to execute queries
                            Statement statement_four = con.createStatement();
                            
                            // SQL query to identify clients who own multiple properties
                            String query4 = "SELECT " +
                                               "o.owner_no, " +
                                               "o.owner_name, " +
                                               "COUNT(*) AS total_properties_owned " +
                                           "FROM " +
                                               "Owner o " +
                                           "JOIN " +
                                               "Properties p ON o.owner_no = p.owner_no " +
                                           "GROUP BY " +
                                               "o.owner_no, " +
                                               "o.owner_name " +
                                           "HAVING " +
                                               "COUNT(*) > 1";
                            
                            // Execute the query and get the ResultSet
                            ResultSet queryResult4 = statement_four.executeQuery(query4);
                            
                            // Print table data
                            System.out.println("\nOwners who own multiple properties:\n");
                            while (queryResult4.next()) {
                                System.out.println("Owner Number: " + queryResult4.getString("owner_no"));
                                System.out.println("Owner Name: " + queryResult4.getString("owner_name"));
                                System.out.println("Total Properties Owned: " + queryResult4.getInt("total_properties_owned"));
                                System.out.println("--------------------");
                            }
                            System.out.println("\n");
                            
                             // Close the ResultSet and Statement
                            queryResult4.close();
                            statement_four.close();
                            
                            break; //END THE CODE FOR NUMBER 4

                        case 5:
                            Scanner myOrder = new Scanner(System.in);
                            boolean isValidChoice = false;
                            String order="";
                            String qOrder = "";

                            //code to decide if its asc or desc order and validate if its just letter a or d
                             while (!isValidChoice) {
                                 System.out.print("Choose the letter A for Ascending order or D for Descending order: \n");
                                 String choice = myOrder.nextLine().toUpperCase();

                                 if (choice.equals("A")) {
                                     System.out.println("\n You chose Ascending order.\n");
                                     order = "Ascending";
                                      qOrder= "ASC";
                                     isValidChoice = true;
                                 } else if (choice.equals("D")) {
                                     System.out.println("\n You chose Descending order. \n");
                                     order = "Descending";
                                     qOrder = "DESC";
                                     isValidChoice = true;
                                 } else {
                                     System.out.println("Error: Please choose either letter A or D. \n");
                                 }
                             }
                                // Create a Statement object to execute queries
                                 Statement stmt = con.createStatement();

                                 // SQL query to list all clients along with the total rent they pay annually
                                 String myQuery = "SELECT c.client_num, c.client_name, SUM(p.monthly_rent * 12) AS total_rent_annually " +
                                                  "FROM Client c " +
                                                  "JOIN Rentals r ON c.client_num = r.client_num " +
                                                  "JOIN Properties p ON r.property_no = p.property_no " +
                                                  "GROUP BY c.client_num, c.client_name " +
                                                  "ORDER BY total_rent_annually "+ qOrder +"";

                                 // Execute the query and get the ResultSet
                                 ResultSet queryResult5 = stmt.executeQuery(myQuery);

                                 // Print table data
                                 System.out.println("Clients along with the total rent they pay annually (sorted in " + order +" order): \n");
                                 while (queryResult5.next()) {
                                     System.out.println("Client Number: " + queryResult5.getString("client_num"));
                                     System.out.println("Client Name: " + queryResult5.getString("client_name"));
                                     System.out.println("Total Rent Annually: " + queryResult5.getDouble("total_rent_annually"));
                                     System.out.println("--------------------");
                                 }

                                 // Close the ResultSet and Statement
                                 queryResult5.close();
                                 stmt.close();
                                 
                            break;//END OF THE CODE NUMBER 5

                        case 6:
                            
                            //START OF THE CODE FOR NUMBER 6
                            // Create a Statement object to execute queries
                            Statement statement_six = con.createStatement();
                            String query6 = "SELECT c.client_num, c.client_name, MAX(p.monthly_rent) AS highest_monthly_rent " +
                                            "FROM Client c " +
                                            "JOIN Rentals r ON c.client_num = r.client_num " +
                                            "JOIN Properties p ON r.property_no = p.property_no " +
                                            "GROUP BY c.client_num, c.client_name " +
                                            "ORDER BY highest_monthly_rent DESC " +
                                            "LIMIT 1";
                            
                            // SQL query to find the client who pays the highest monthly rent
                            ResultSet queryResult6 = statement_six.executeQuery(query6);
                            
                            // Print table data
                            System.out.println("\nClient who pays the highest monthly rent:\n");
                            if (queryResult6.next()) {
                                System.out.println("Client Number: " + queryResult6.getString("client_num"));
                                System.out.println("Client Name: " + queryResult6.getString("client_name"));
                                System.out.println("Highest Monthly Rent: " + queryResult6.getDouble("highest_monthly_rent"));
                            } else {
                                System.out.println("No clients found.");
                            }
                            System.out.println("\n");
                            
                            // Close the ResultSet and Statement
                            queryResult6.close();
                            statement_six.close();
                            
                            break; //END OF THE CODE FOR NUMBER 6

                        case 7:
                            // Begin by prompting the user to enter the initial letter of the client's name
                                Scanner myInput = new Scanner(System.in);
                                String initialLetter = "";
                                boolean validInput = false;

                                // Check if the input contains only a single letter
                                do {
                                    try {
                                        System.out.print("Enter the initial letter of the client's name: ");
                                        initialLetter = myInput.nextLine().toUpperCase();
                                        if (initialLetter.length() == 1 && Character.isLetter(initialLetter.charAt(0))) {
                                            validInput = true;
                                        } else {
                                            System.out.println("\n Please enter a single letter. \n");
                                        }
                                    } catch (Exception e) {
                                        // Handle any exceptions and prompt the user to try again
                                        System.out.println("Invalid input. Please try again.");
                                        myInput.nextLine(); // consume invalid input
                                    }
                                } while (!validInput);

                                // Create a Statement object to execute the SQL query
                                Statement statement_seven = con.createStatement();
                                String query7 = "SELECT c.client_name, p.property_no, p.property_address " +
                                                "FROM Client c " +
                                                "JOIN Rentals r ON c.client_num = r.client_num " +
                                                "JOIN Properties p ON r.property_no = p.property_no " +
                                                "WHERE c.client_name LIKE '" + initialLetter + "%' " +
                                                "ORDER BY c.client_name, p.property_no";

                                // Execute the query and get the ResultSet
                                ResultSet queryResult7 = statement_seven.executeQuery(query7);

                                boolean foundClients = false;
                                // Print the properties rented out by clients whose names begin with the initial letter
                                System.out.println("\nProperties rented out by clients whose name begins with '" + initialLetter + "':\n");
                                while (queryResult7.next()) {
                                    foundClients = true;
                                    System.out.println("Client Name: " + queryResult7.getString("client_name"));
                                    System.out.println("Property Number: " + queryResult7.getString("property_no"));
                                    System.out.println("Property Address: " + queryResult7.getString("property_address"));
                                    System.out.println("--------------------");
                                }

                                // If no clients are found, print a message indicating so
                                if (!foundClients) {
                                    System.out.println("There are no clients with names beginning with '" + initialLetter + "'.");
                                }
                                System.out.println("\n");

                                // Close the ResultSet and Statement
                                queryResult7.close();
                                statement_seven.close();
                                // End of the code for task number 7
                            break;




                        case 8:
                            System.out.println("\n --End of the program.-- \n");
                            break;

                        default:
                            System.out.println("Not in the options");
                    }

                } catch (Exception e) {
                    System.out.println("It's not a valid number, try any number from 1 to 8");
                    myScanner.next();
                }
            } while (chooseNumber != 8);

        } catch (SQLException e) {
            System.out.println("SQL Error -->");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Error --" + e.getMessage());
        }
            // end of the code to connect the database
    }
    
}
