package com.mygame.dto.general;

import com.mygame.entity.general.NotificationType;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class NotificationRequest {
    private Long userId;
    private NotificationType type;
    private String message;
    @Nullable
    private String anchor;
    private Integer status;
}
