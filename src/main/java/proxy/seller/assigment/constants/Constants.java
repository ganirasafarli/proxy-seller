package proxy.seller.assigment.constants;

public class Constants {
    private Constants() {
    }

    public static final String VALID_PASSWORD_REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*[&%$]).{6,}$";
    public static final String VALID_PASSWORD_MESSAGE = "Your password include at " +
            "least one upper, one lower and one numeric character.";
    public static final String VALID_EMAIL_MESSAGE = "Email is not valid, please check your email.";
    public static final String HEADER_KEY_AUTHORIZATION = "Authorization";
    public static final String HEADER_VALUE_AUTHORIZATION_PREFIX = "Bearer ";
}
