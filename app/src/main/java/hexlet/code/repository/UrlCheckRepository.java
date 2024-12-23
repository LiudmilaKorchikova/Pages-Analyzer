package hexlet.code.repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import hexlet.code.model.UrlCheck;

public class UrlCheckRepository extends BaseRepository {
    private static final Logger LOGGER = Logger.getLogger(UrlRepository.class.getName());

    public static void save(UrlCheck check) throws SQLException {
        var sql = "INSERT INTO url_checks (url_id, status_code, title, h1, description, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, check.getUrlId());
            preparedStatement.setLong(2, check.getStatusCode());
            preparedStatement.setString(3, check.getTitle());
            preparedStatement.setString(4, check.getH1());
            preparedStatement.setString(5, check.getDescription());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(check.getCreatedAt()));

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                check.setId(generatedKeys.getLong(1));

                LOGGER.info("Check saved: " + check.getTitle() + " " + check.getH1());
            } else {
                throw new SQLException("DB did not return an ID after saving an entity");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving Check: " + check.getTitle() + " " + check.getId(), e);
            throw e;
        }
    }

    public static List<UrlCheck> getEntitiesForThisUrl(Long urlId) throws SQLException {
        var sql = "SELECT id, url_id, status_code, title, h1, description, created_at FROM url_checks WHERE url_id = ?";
        List<UrlCheck> checks = new ArrayList<>();
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setLong(1, urlId);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UrlCheck check = new UrlCheck(
                            resultSet.getLong("url_id"),
                            resultSet.getInt("status_code"),
                            resultSet.getString("title"),
                            resultSet.getString("h1"),
                            resultSet.getString("description")
                    );
                    check.setId(resultSet.getLong("id"));
                    checks.add(check);
                }
            }
        }
        return checks;
    }
}
