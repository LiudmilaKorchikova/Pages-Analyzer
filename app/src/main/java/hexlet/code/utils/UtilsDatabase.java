package hexlet.code.utils;

import hexlet.code.App;
import hexlet.code.repository.BaseRepository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

public class UtilsDatabase {
    public static void init() throws Exception {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream("urls.sql");
        if (inputStream == null) {
            throw new Exception("SQL script not found in resources!");
        }

        String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        try (Connection connection = BaseRepository.dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
    }

    public static void clear() {
        try (Connection connection = BaseRepository.dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM url_checks");
            statement.executeUpdate("DELETE FROM urls");
        } catch (Exception e) {
            System.err.println("Failed to clear database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
