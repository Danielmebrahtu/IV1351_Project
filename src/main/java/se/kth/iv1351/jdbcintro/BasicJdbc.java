/*
 * The MIT License (MIT)
 * Copyright (c) 2020 Leif Lindbäck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction,including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so,subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package se.kth.iv1351.jdbcintro;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * A small program that illustrates how to write a simple JDBC program.
 */
public class BasicJdbc {
  private static final String TABLE_NAME = "personTest";
  private static final String TABLE_INSTRUMENT = "rental_of_instrument";
  private static final String COLUMN_STUDENT = "student_id";
  private static final String COLUMN_RENTED = "rented";
  private static final String TABLE_LEASE = "lease_period";
  private static final String COLUMN_ID = "id";
  private static final String COLUMN_TERMINATED = "teminated";
  private static final String COLUMN_RENTEDID = "rental_of_instrument_id";
  private PreparedStatement listInstrumentStmt;
  private PreparedStatement checkLimitStmt;
  private PreparedStatement rentInstrumentStmt;
  private PreparedStatement updateLeaseStmt;
  private PreparedStatement terminateLeaseStmt;
  private PreparedStatement terminateRentalStmt;
  private PreparedStatement terminateRentalStmt2;



  private void accessDB() {
    try (Connection connection = createConnection()) {
      connection.setAutoCommit(false);
      createTable(connection);
      prepareStatements(connection);
      Scanner scanner = new Scanner(System.in);

      System.out.println("\n \n Task 1. List instruments" +
                            "\n Task 2. Rent instrument" +
                            "\n Task 3. Terminate rental" +
                            "\n Option 4. Exit website");
      int task = scanner.nextInt();
      switch(task) {
        case 1:
          System.out.println("What instrument?");
          String instrument = scanner.next();

          listInstrumentStmt.setString(1, instrument);
          listInstrumentStmt.setString(2, "No");

          listAllInstruments();
          break;
        case 2:
          System.out.println("What is your student id?");
          int studentId = scanner.nextInt();
          System.out.println("What instrument would you like to rent?");
          int instrumentId = scanner.nextInt();


          checkLimitStmt.setInt(1, studentId);

          rentInstrumentStmt.setInt(1, studentId);
          rentInstrumentStmt.setString(2, "Yes");
          rentInstrumentStmt.setInt(3, instrumentId);

          updateLeaseStmt.setInt(1, instrumentId);
          updateLeaseStmt.setInt(2, 2);
          updateLeaseStmt.setDate(3,  java.sql.Date.valueOf("2021-05-17"));
          updateLeaseStmt.setString(4, "False");

          checkLimit(connection);

          break;
        case 3:
          System.out.println("What instrument would you like to return?");
          int terminateInstrumentId = scanner.nextInt();

          terminateLeaseStmt.setString(1,"True");
          terminateLeaseStmt.setInt(2,terminateInstrumentId);
          terminateLeaseStmt.setString(3,"False");

          //terminateRentalStmt.setString(1, "No");
          //terminateRentalStmt.setInt(2, terminateInstrumentId);

          terminateRentalStmt2.setString(1, "No");
          terminateRentalStmt2.setInt(2, terminateInstrumentId);

          terminateRental(connection);

          break;
        case 4:
          System.out.println("You have left the Soundgood music website");

          break;
        default:
          System.out.println("!!WRONG INPUT!!");
      }
    } catch (SQLException | ClassNotFoundException exc) {
      exc.printStackTrace();
    }
  }

  private Connection createConnection() throws SQLException, ClassNotFoundException {
    Class.forName("org.postgresql.Driver");
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/databas7",
            "postgres", "example");
    // Class.forName("com.mysql.cj.jdbc.Driver");
    // return DriverManager.getConnection(
    // "jdbc:mysql://localhost:3306/simplejdbc?serverTimezone=UTC",
    // "root", "javajava");
  }


  private void createTable(Connection connection) {
    try (Statement stmt = connection.createStatement()) {
      if (!tableExists(connection)) {
        stmt.executeUpdate(
                "create table " + TABLE_NAME + " (name varchar(32) primary key, phone varchar(12), age int)");
      }
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }

  private boolean tableExists(Connection connection) throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet tableMetaData = metaData.getTables(null, null, null, null);
    while (tableMetaData.next()) {
      String tableName = tableMetaData.getString(3);
      if (tableName.equalsIgnoreCase(TABLE_NAME)) {
        return true;
      }
    }
    return false;
  }

  private void listAllInstruments() {
    try (ResultSet instruments = listInstrumentStmt.executeQuery()) {
      while(instruments.next()) {
        System.out.println("Id: " + instruments.getInt(1) +
                          ", Type: " + instruments.getString(2) +
                          ", Brand: " + instruments.getString(3) +
                          ", Price: " + instruments.getInt(4));


        System.out.println();
        System.out.println();

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void checkLimit (Connection connection) throws SQLException {
    //int checkValeu1 = 1;
      int checkValeu2 = 2;
    //int checkValeu3 = 3;
    try(ResultSet nrOfInstrumentsRented = checkLimitStmt.executeQuery()){
      while(nrOfInstrumentsRented.next()) {
        System.out.println("Count: " + nrOfInstrumentsRented.getInt(1));

        /* //Endast Tester
        if (nrOfInstrumentsRented.getInt(1) == checkValeu2) {

          System.out.println("Test 1");
        } else if (nrOfInstrumentsRented.getInt(1) <= checkValeu) {
          System.out.println("Test 2");
        }

        */

        if(nrOfInstrumentsRented.getInt(1) < checkValeu2){
          System.out.println("You have rented " +
                   nrOfInstrumentsRented.getInt(1) + " instrument(s)");

          rentInstrumentStmt.executeUpdate();
          updateLeaseStmt.executeUpdate();
          System.out.println("Instrument has been rented");
          connection.commit();

        } else{
          System.out.println("You have already rented 2 instruments");
        }
      }
    }catch(SQLException e){
      e.printStackTrace();
      connection.rollback();
    }
  }

  private void terminateRental(Connection connection) throws SQLException {
    try {
      terminateLeaseStmt.executeUpdate();
      terminateRentalStmt2.executeUpdate();
      connection.commit();
      System.out.println("Your rental has been terminated");

    } catch(SQLException e) {
      e.printStackTrace();
      connection.rollback();
      System.out.println("Rental was not found");
      //System.out.println("Fel 5 i  terminateRental ");

    }
  }

/*

  private void rentAnInstrument(Connection connection) throws SQLException{
    try (ResultSet rentedInstrument = rentInstrumentStmt.executeQuery()){
      while(rentedInstrument.next()){
        System.out.println("Test 1");
        rentInstrumentStmt.executeUpdate();
        connection.commit();
      }
    } catch(SQLException e) {
      System.out.println("Test 2");

      e.printStackTrace();
      connection.rollback();
    }

    try(ResultSet updateLease = updateLeaseStmt.executeQuery()){
      while(updateLease.next()){
        System.out.println("Test 3");
        updateLeaseStmt.executeUpdate();
        connection.commit();
      }
    } catch(SQLException e) {
      System.out.println("Test 4");
      e.printStackTrace();
      connection.rollback();
    }

  }

 */


  private void prepareStatements(Connection connection) throws SQLException {

    listInstrumentStmt = connection.prepareStatement("SELECT * FROM " + TABLE_INSTRUMENT + " WHERE type_of_instrument = ? AND rented = ?");
    checkLimitStmt = connection.prepareStatement("SELECT COUNT (" + TABLE_INSTRUMENT + "." + COLUMN_STUDENT + ")"
                                                   + " FROM " + TABLE_INSTRUMENT +
                                                     " WHERE "  + TABLE_INSTRUMENT + "." + COLUMN_STUDENT + " = ?");

    rentInstrumentStmt = connection.prepareStatement("UPDATE " + TABLE_INSTRUMENT + " SET " + COLUMN_STUDENT + " = ?," + COLUMN_RENTED + " = ? WHERE " +
                                                       TABLE_INSTRUMENT + "." + COLUMN_ID + " = ?");

    updateLeaseStmt = connection.prepareStatement("INSERT INTO " + TABLE_LEASE +  " (rental_of_instrument_id, max_lease_period, start_of_rental, teminated)" +
                                                  "VALUES ( ?,?,?,?) ");

    terminateLeaseStmt = connection.prepareStatement("UPDATE " + TABLE_LEASE + " SET " + COLUMN_TERMINATED + " = ? WHERE " +
                                                    COLUMN_RENTEDID + " = ? AND " + COLUMN_TERMINATED + " = ?");

    terminateRentalStmt = connection.prepareStatement("UPDATE " + TABLE_INSTRUMENT + " SET " + COLUMN_RENTED +
                                                      " ? WHERE " + COLUMN_ID + " = ?");


    terminateRentalStmt2 = connection.prepareStatement("UPDATE rental_of_instrument SET rented = ? WHERE id = ?");
  }

  public static void main(String[] args) {

    new BasicJdbc().accessDB();

  }
}