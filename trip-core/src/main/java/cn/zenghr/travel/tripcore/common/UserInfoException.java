package cn.zenghr.travel.tripcore.common;

public class UserInfoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String message;

    public UserInfoException(String message) {
        this.message = message;
    }

    public UserInfoException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
