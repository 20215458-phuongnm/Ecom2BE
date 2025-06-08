package com.mygame.service.general;

import com.mygame.dto.general.NotificationResponse;

public interface NotificationService {

    void pushNotification(String uniqueKey, NotificationResponse notification);

}
