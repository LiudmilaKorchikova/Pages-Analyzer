package hexlet.code.utils;

public class NamedRoutes {

    //urls list
    public static String urlsPath() {
        return "/urls";
    }

    //one url with number
    public static String urlPath(Long id) {
        return urlPath(String.valueOf(id));
    }

    //one url with text
    public static String urlPath(String id) {
        return urlsPath() + "/" + id;
    }

    public static String addUrlPath() {
        return "urls/add";
    }
}
