package econo.app.sleeper.web.sleep;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class SleepResponse {

    private final String message;
    private final Long sleepPk;

    public static SleepResponse of(String message, Long sleepPk){
        return SleepResponse.builder()
                .message(message)
                .sleepPk(sleepPk)
                .build();
    }

}
