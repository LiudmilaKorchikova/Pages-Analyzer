package hexlet.code.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();

        // Подключение через переменную окружения JDBC_DATABASE_URL
        String jdbcUrl = System.getenv("JDBC_DATABASE_URL");
        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            throw new IllegalStateException("JDBC_DATABASE_URL is not set");
        }

        // Настройка источника данных
        config.setJdbcUrl(jdbcUrl);
        config.setMaximumPoolSize(10); // Настройте по своему усмотрению
        config.setConnectionTimeout(30000); // Настройка тайм-аута на подключение

        dataSource = new HikariDataSource(config);
    }

    // Метод для получения соединения с базой данных
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Закрытие источника данных
    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
