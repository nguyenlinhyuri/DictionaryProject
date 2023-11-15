package com.example.mydictionary.jdbc;

import java.sql.*;
import java.util.*;
public class JdbcDao {
    public static final String URL = "jdbc:mysql://localhost:3306/dictionary?useSSL=false";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "Klinh2004//";
    public static final String INSERT_QUERY = "INSERT INTO NotedWords (vocab, meaning) VALUES (?, ?)";
    public static final String DELETE_QUERY = "DELETE FROM NotedWords WHERE vocab = ?";
    public static final String UPDATE_QUERY = "UPDATE NotedWords SET meaning = ? WHERE vocab = ?";
    public static final String SELECT_ALL_QUERY = "SELECT vocab FROM NotedWords";
    public static final String SELECT_MEANING_QUERY = "SELECT meaning FROM NotedWords WHERE vocab = ?";

    /**
     * thêm từ
     */
    public void addWordToDatabase(String vocab, String meaning) throws SQLException {
        try (Connection connection = DriverManager
                .getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, vocab);
            preparedStatement.setString(2, meaning);

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                System.out.println("GeneratedId: " + generatedId);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    /**
     * xóa từ
     */
    public void deleteWordFromDatabase(String word) throws SQLException {
        try (Connection connection = DriverManager
                .getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pst = connection.prepareStatement(DELETE_QUERY)) {
            pst.setString(1, word);
            System.out.println(pst);

            int rowDeleted = pst.executeUpdate();
            if (rowDeleted > 0) {
                System.out.println("delete successfully!");
            } else System.out.println("no matching word found");
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    /**
     * cập nhật nghĩa cho từ
     */
    public void updateWordInDatabase(String vocab, String newMeaning) throws SQLException {
        try (Connection connection = DriverManager
                .getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pst = connection.prepareStatement(UPDATE_QUERY)) {
            pst.setString(1, newMeaning);
            pst.setString(2, vocab);

            System.out.println(pst);

            int rowUpdated = pst.executeUpdate();

            if (rowUpdated > 0) System.out.println("update successfully!");
            else System.out.println("No matching word found");
        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    /**
     * lấy ra danh sách tất cả các từ
     */
    public List<String> getAllWords() throws SQLException{
        List<String> vocabList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String vocab = resultSet.getString("vocab");
                vocabList.add(vocab);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return vocabList;
    }

    /**
     * lấy ra nghĩa của từ vocab
     */
    public String getMeaning(String vocab) throws SQLException{
        String meaning = null;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MEANING_QUERY)) {

            preparedStatement.setString(1, vocab);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    meaning = resultSet.getString("meaning");
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return meaning;
    }

    /**
     * in ra thông tin của lỗi
     */
    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
