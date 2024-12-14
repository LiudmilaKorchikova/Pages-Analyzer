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

    //start check with number
    public static String urlChecksPath(Long id) {
        return urlChecksPath(String.valueOf(id));
    }

    //start check with text
    public static String urlChecksPath(String id) {
        return "/urls/" + id + "/checks";
    }
}
