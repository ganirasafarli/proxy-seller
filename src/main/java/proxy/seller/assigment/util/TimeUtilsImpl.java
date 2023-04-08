package proxy.seller.assigment.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
@Component
public class TimeUtilsImpl implements TimeUtils {
    @Override
    public Long getEpochMillis() {
        return Instant.now(Clock.systemUTC()).toEpochMilli();
    }

    @Override
    public Long getEpochSeconds() {
        return Instant.now(Clock.systemUTC()).getEpochSecond();
    }
}
