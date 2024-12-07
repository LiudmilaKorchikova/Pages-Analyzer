package hexlet.code.utils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceProvider {
    private static final String DATABASE_URL = System.getenv("JDBC_DATABASE_URL");

    public static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();

        if (DATABASE_URL != null && !DATABASE_URL.isEmpty()) {
            config.setJdbcUrl(DATABASE_URL);
        } else {
            // Локальная база данных H2
            config.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1");
            config.setDriverClassName("org.h2.Driver");
            config.setUsername("sa");
            config.setPassword("");
        }

        config.setMaximumPoolSize(10);
        config.setAutoCommit(false);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }
}
