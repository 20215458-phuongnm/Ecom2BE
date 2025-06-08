package com.mygame.controller.client;

import com.mygame.constant.AppConstants;
import com.mygame.constant.FieldName;
import com.mygame.constant.ResourceName;
import com.mygame.dto.ListResponse;
import com.mygame.dto.general.EventInitiationResponse;
import com.mygame.dto.general.NotificationRequest;
import com.mygame.dto.general.NotificationResponse;
import com.mygame.entity.general.Notification;
import com.mygame.exception.ResourceNotFoundException;
import com.mygame.mapper.general.NotificationMapper;
import com.mygame.repository.general.NotificationRepository;
import com.mygame.service.general.EmitterService;
import com.mygame.service.general.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client-api/notifications")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
@Slf4j
public class ClientNotificationController {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final EmitterService emitterService;
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ListResponse<NotificationResponse>> getAllNotifications(
            Authentication authentication,
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = AppConstants.DEFAULT_SORT) String sort,
            @RequestParam(name = "filter", required = false) @Nullable String filter
    ) {
        String username = authentication.getName();
        Page<Notification> notifications = notificationRepository.findAllByUsername(username, sort, filter, PageRequest.of(page - 1, size));
        List<NotificationResponse> notificationResponses = notifications.map(notificationMapper::entityToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ListResponse.of(notificationResponses, notifications));
    }

    // Reference: https://stackoverflow.com/a/62749980
    @GetMapping("/init-events")
    public ResponseEntity<EventInitiationResponse> initNotificationEvents(Authentication authentication) {
        String username = authentication.getName();

        String eventSourceUuid;

        if (emitterService.isExistEmitterByUniqueKey(username)) {
            eventSourceUuid = emitterService.getEmitterUuidByUniqueKey(username);
        } else {
            eventSourceUuid = UUID.randomUUID().toString();
            emitterService.createEmitter(eventSourceUuid, username);
        }

        EventInitiationResponse eventInitiationResponse = new EventInitiationResponse(eventSourceUuid);
        return ResponseEntity.status(HttpStatus.OK).body(eventInitiationResponse);
    }

    @GetMapping("/events")
    public SseEmitter subscribeNotificationEvents(@RequestParam String eventSourceUuid) {
        return emitterService.getEmitterByUuid(eventSourceUuid);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(@PathVariable Long id,
                                                                   @RequestBody NotificationRequest request) {
        NotificationResponse notificationResponse = notificationRepository
                .findById(id)
                .map(existingEntity -> notificationMapper.partialUpdate(existingEntity, request))
                .map(notificationRepository::save)
                .map(notificationMapper::entityToResponse)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceName.NOTIFICATION, FieldName.ID, id));
        return ResponseEntity.status(HttpStatus.OK).body(notificationResponse);
    }

    @PostMapping("/push-events")
    public ResponseEntity<NotificationResponse> pushNotification(@RequestBody NotificationRequest request) {
        Notification notification = notificationRepository.save(notificationMapper.requestToEntity(request));
        NotificationResponse notificationResponse = notificationMapper.entityToResponse(notification);
        notificationService.pushNotification(notification.getUser().getUsername(), notificationResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationResponse);
    }

}
