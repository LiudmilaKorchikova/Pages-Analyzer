package hexlet.code.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

import hexlet.code.App;
import hexlet.code.repository.BaseRepository;

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
}
