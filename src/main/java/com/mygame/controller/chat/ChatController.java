package com.mygame.controller.chat;

import com.mygame.constant.AppConstants;
import com.mygame.dto.ListResponse;
import com.mygame.dto.chat.MessageRequest;
import com.mygame.dto.chat.MessageResponse;
import com.mygame.service.chat.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;
    private MessageService messageService;

    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, @Payload MessageRequest message) {
        MessageResponse messageResponse = messageService.save(message);
        simpMessagingTemplate.convertAndSend("/chat/receive/" + roomId, messageResponse);
    }

    @GetMapping("/messages")
    public ResponseEntity<ListResponse<MessageResponse>> getAllMessages(
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "sort", defaultValue = AppConstants.DEFAULT_SORT) String sort,
            @RequestParam(name = "filter", required = false) @Nullable String filter,
            @RequestParam(name = "search", required = false) @Nullable String search,
            @RequestParam(name = "all", required = false) boolean all
    ) {
        ListResponse<MessageResponse> messageResponses = messageService.findAll(page, size, sort, filter, search, all);
        return ResponseEntity.status(HttpStatus.OK).body(messageResponses);
    }

}
