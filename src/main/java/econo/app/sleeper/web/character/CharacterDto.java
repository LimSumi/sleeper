package econo.app.sleeper.web.character;

import econo.app.sleeper.domain.character.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CharacterDto {

    private final String userId;
    private final String content;

    public static CharacterDto of(String userId, String content){
        return CharacterDto.builder()
                .userId(userId)
                .content(content)
                .build();
    }
}
