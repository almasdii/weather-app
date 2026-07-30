package weather.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SessionParameters {
    public final String SESSION_UUID = "SessionUUID";
    public final Long MAX_SESSION_MINUTES = 20L;
    public final Integer MAX_SESSION_SECONDS = 1200;
    public final Integer EXPIRES_SESSION = 0;
    public final Boolean SET_HTTP_ONLY = true;
    public final Boolean SET_SECURE = true;
    public final String PATH = "/weather";
}
