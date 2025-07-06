package com.mygame.service.general;

import com.mygame.dto.general.NotificationResponse;
import com.mygame.mapper.general.EventMapper;
import com.mygame.repository.general.EmitterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Primary
@AllArgsConstructor
@Slf4j
public class SseNotificationService implements NotificationService {

    private final EmitterRepository emitterRepository;
    private final EventMapper eventMapper;


    @Override
    public void pushNotification(String uniqueKey, NotificationResponse notification) {
        emitterRepository
                .getByUniqueKey(uniqueKey)
                .ifPresentOrElse(emitter -> {
                    try {
                        log.debug("Sending event: {} for key: {}", notification, uniqueKey);
                        // Reference: https://stackoverflow.com/a/42803814
                        emitter.send(eventMapper.toSseEventBuilder("message", notification)); //send noti
                    } catch (IOException | IllegalStateException e) {
                        log.debug("Error while sending event: {} for key: {} - exception: {}", notification, uniqueKey, e);
                        emitterRepository.remove(uniqueKey); //Lỗi, xóa emiiter
                    }
                }, () -> log.debug("No emitter for key: {}", uniqueKey));
    }

}
