package constants;

public class Constants {

    public static final String host = System.getenv("MYSQL_SERVICE_HOST");
    public static final int port = 3306;

    public static final String CONNECTING_URL = String.format("jdbc:mysql://%s:%s/sequoia", host, port);
    public static final String CONNECTING_USER = "userC8N";
    public static final String CONNECTING_PASSWORD = "AuclGGuTEUFX14Gf";
/*
    // localhost
    public static final String CONNECTING_URL = "jdbc:mysql://localhost:3306/sequoia";
    public static final String CONNECTING_USER = "root";
    public static final String CONNECTING_PASSWORD = "";
*/
    public static final int CONNECTING_POOL_SIZE = 5;

    public static final String CONTEXT = "CONTEXT";
    public static final String VALIDATION_MESSAGE = "VALIDATION_MESSAGE";
    public static final String CURRENT_SESSION_ACCOUNT = "CURRENT_SESSION_ACCOUNT";
    public static final String CURRENT_ROLE = "CURRENT_ROLE";
    public static final String CURRENT_MAPPING = "CURRENT_MAPPING";

    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_STUDENT = 2;

}
