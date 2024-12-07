package hexlet.code.repository;

//import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.utils.Database;
import io.javalin.http.Context;

import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrlRepository extends BaseRepository {

    private static final Logger LOGGER = Logger.getLogger(UrlRepository.class.getName());

    public static String addUrl(String inputUrl, Context ctx) {
        String resultMessage = "";
        try {
            URL url = URI.create(inputUrl).toURL(); // Используем toURL для парсинга
            String domain = getDomainWithProtocol(url);

            if (urlExists(domain)) {
                resultMessage = "Страница уже существует";
                ctx.sessionAttribute("flashMessage", resultMessage); // Flash-сообщение
            } else {
                addUrlToDatabase(domain);
                resultMessage = "Страница успешно добавлена";
                ctx.sessionAttribute("flashMessage", resultMessage); // Flash-сообщение
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Некорректный URL: " + inputUrl, e);
            resultMessage = "Некорректный URL";
            ctx.sessionAttribute("flashMessage", resultMessage); // Flash-сообщение
        }
        return resultMessage;
    }

    private static String getDomainWithProtocol(URL url) {
        String protocol = url.getProtocol();
        String host = url.getHost();
        int port = url.getPort();

        StringBuilder domain = new StringBuilder(protocol + "://" + host);

        if (port != -1) {
            domain.append(":" + port);
        }

        return domain.toString();
    }

    /*private static void addUrlToDatabase(String domain) {
        String query = "INSERT INTO urls (name, created_at) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, domain);
            statement.setString(2, getCurrentTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении в базу данных", e);
        }
    }

    private static boolean urlExists(String domain) {
        String query = "SELECT COUNT(*) FROM urls WHERE name = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, domain);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при проверке существования URL", e);
        }
        return false;
    }*/

    public static boolean urlExists(String domain) {
        String query = "SELECT COUNT(*) FROM urls WHERE name = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, domain);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Пример добавления URL
    public static void addUrlToDatabase(String domain) {
        String query = "INSERT INTO urls (name, created_at) VALUES (?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, domain);
            statement.setString(2, new Timestamp(System.currentTimeMillis()).toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    public static List<String> getAllUrls() {
        List<String> urls = new ArrayList<>();
        String query = "SELECT name FROM urls";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                urls.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении URL из базы данных", e);
        }
        return urls;
    }

    public static String getUrlById(int id) {
        String url = null;
        String query = "SELECT name FROM urls WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                url = resultSet.getString("name");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении URL по ID", e);
        }
        return url;
    }
}
