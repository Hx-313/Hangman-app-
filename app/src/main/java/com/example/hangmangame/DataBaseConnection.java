package com.example.hangmangame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataBaseConnection {
    private String Name;
    private String Mode;
    private String Word;
    private int tries;
    List<Object[]> dataListUpdate;


    private Connection connection;
    private PreparedStatement preparedStatement;

    // Constructor to establish database connection and create PreparedStatement
    public DataBaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hangaroo","root","");

            // Assume you have a 'LeaderBoard' table with columns matching the expected order
            String sql = "SELECT * FROM leaderBoard";
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection failure as needed
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle connection failure as needed
        }
    }

    // Method to fetch data from the database
    public List<Object[]> getDataFromDatabase() {
        List<Object[]> dataList = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Assuming the order of columns is: Player Rank, Player Name, Mode, Word to Guess, Tries Used
                Object[] rowData = new Object[]{
                        resultSet.getInt("PlayerRank"),
                        resultSet.getString("PlayerName"),
                        resultSet.getString("Mode"),
                        resultSet.getString("WordToGuess"),
                        resultSet.getInt("TriesUsed")
                };

                dataList.add(rowData);
            }



        } catch (SQLException  e) {
            e.printStackTrace();
            // Handle exception as needed
        }

        return dataList;
    }

    public void settingData(){
       /* Name = GameStats.getName();
        Word = WordFromHum.getWordFromHum();
        tries = GameStats.getTries();
        Mode = GameMode.getGameMode();
        dataListUpdate = getDataFromDatabase() ;
        updateStats();*/
    }

    public void updateStats(){
        String tempName;
        String tempMode;
        String tempword;
        int tempTries;
        for(Object [] row : dataListUpdate){
            int Tries = (int) row[4];
            if( Tries > tries){
                tempName = (String) row[1];
                tempMode = (String) row[2];
                tempword = (String) row[3];
                tempTries = (int) row[4];

                row[1] = Name;
                row [2] =Mode;
                row [3] = Word;
                row [4] = tries;

                Name = tempName;
                Word = tempword;
                Mode = tempMode;
                tries = tempTries;


            }
        }
        allotingRank();

    }
    public void allotingRank(){
        Collections.sort(dataListUpdate, Comparator.comparingInt(row -> (int) row[4]));

        int rank = 1;
        for (Object[] row : dataListUpdate) {
            row[0] = rank++;
            UpdateData();
        }
    }
    public void UpdateData(){
        String updateQuery = "UPDATE leaderboard SET PlayerName=?, Mode=?, WordToGuess=?, TriesUsed=? WHERE PlayerRank=?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            for (Object[] row : dataListUpdate) {
                // Set the values for the update statement
                updateStatement.setString(1, (String) row[1]);  // PlayerName
                updateStatement.setString(2, (String) row[2]);  // Mode
                updateStatement.setString(3, (String) row[3]);  // WordToGuess
                updateStatement.setInt(4, (int) row[4]);        // TriesUsed
                updateStatement.setInt(5, (int) row[0]);        // PlayerRank

                // Execute the update statement
                updateStatement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Close the connection and prepared statement when done
    public void closeConnection() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle closing connection failure as needed
        }
    }
    public static void main (String [] args){
        new DataBaseConnection();
    }



}
