package by.belnarto.telegrambot.service.exception;

public class CityNotFoundException extends Exception {
    public CityNotFoundException() {
        super();
    }

    public CityNotFoundException(final String message) {
        super(message);
    }

    public CityNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CityNotFoundException(final Throwable cause) {
        super(cause);
    }
}
