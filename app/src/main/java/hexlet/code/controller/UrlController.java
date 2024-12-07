package hexlet.code.controller;

import io.javalin.http.Context;
import hexlet.code.repository.UrlRepository;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class UrlController {

    // Обработчик для добавления URL
    public static void addUrlHandler(Context ctx) {
        String url = ctx.formParam("url");

        if (url == null || url.isEmpty()) {
            // Если URL пустой, выводим сообщение
            ctx.sessionAttribute("flashMessage", "URL не может быть пустым.");
            ctx.redirect("/add-url"); // Перенаправляем на страницу ввода
            return;
        }

        try {
            // Преобразуем URL в объект и получаем домен
            URL parsedUrl = new URI(url).toURL();
            String domain = parsedUrl.getProtocol() + "://" + parsedUrl.getHost();
            if (parsedUrl.getPort() != -1) {
                domain += ":" + parsedUrl.getPort();
            }

            // Добавляем домен в базу данных
            String resultMessage = UrlRepository.addUrl(domain, ctx); // Передаем ctx для работы с сессией

            // Перенаправляем обратно на страницу ввода
            ctx.redirect("/add-url");
        } catch (Exception e) {
            // Если URL некорректный, выводим сообщение об ошибке
            ctx.sessionAttribute("flashMessage", "Некорректный URL.");
            ctx.redirect("/add-url"); // Перенаправляем обратно на страницу ввода
        }
    }

    public static void showUrlsHandler(Context ctx) {
        // Получаем все URL из базы данных
        List<String> urls = UrlRepository.getAllUrls();

        // Передаем список URL в шаблон
        ctx.attribute("urls", urls);
        ctx.render("/urls"); // Отображаем шаблон на странице /urls
    }

    public static void showUrlByIdHandler(Context ctx) {
        // Получаем ID из пути
        int id = Integer.parseInt(ctx.pathParam("id"));

        // Получаем URL по ID из базы данных
        String url = UrlRepository.getUrlById(id);

        if (url != null) {
            // Передаем URL в шаблон
            ctx.attribute("url", url);
            ctx.render("/show-url"); // Отображаем шаблон для конкретного URL
        } else {
            // Если URL не найден, выводим сообщение об ошибке
            ctx.sessionAttribute("flashMessage", "URL не найден.");
            ctx.redirect("/urls"); // Перенаправляем на страницу со списком URL
        }
    }
}
