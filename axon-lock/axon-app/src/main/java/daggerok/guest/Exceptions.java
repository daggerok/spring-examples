package daggerok.guest;

import java.time.LocalDateTime;

import static java.lang.String.format;

public class Exceptions {

  public static class GuestAccessExpiredException extends IllegalStateException {

    private static final long serialVersionUID = -7585546291040734048L;

    private GuestAccessExpiredException(final String message) {
      super(message);
    }

    public static GuestAccessExpiredException of(final String guestId, final LocalDateTime expireAt) {
      return new GuestAccessExpiredException(format("guest %s access expired at %s.", guestId, expireAt));
    }
  }

  public static class GuestInactiveException extends IllegalStateException {

    private static final long serialVersionUID = -7585546291040734048L;

    private GuestInactiveException(final String message) {
      super(message);
    }

    public static GuestInactiveException of(final String guestId) {
      return new GuestInactiveException(format("guest %s inactive.", guestId));
    }
  }

  private Exceptions() {}
}
