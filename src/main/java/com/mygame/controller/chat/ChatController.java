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

    /**
     * Gửi tin nhắn đến phòng chat:
     * - Sử dụng WebSocket (`@MessageMapping`) để lắng nghe tin nhắn gửi đến room có roomId.
     * - Nhận `MessageRequest` từ client, gọi `messageService.save()` để lưu vào DB.
     * - Gửi phản hồi (`MessageResponse`) đến tất cả client đang lắng nghe `/chat/receive/{roomId}`.
     *
     * @param roomId ID của phòng chat
     * @param message Nội dung tin nhắn từ client
     */
    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, @Payload MessageRequest message) {
        MessageResponse messageResponse = messageService.save(message);
        simpMessagingTemplate.convertAndSend("/chat/receive/" + roomId, messageResponse);
    }

    /**
     * Lấy danh sách tất cả tin nhắn:
     *
     * @param page Số trang (mặc định lấy từ `AppConstants`)
     * @param size Số lượng item mỗi trang (mặc định 20)
     * @param sort Cách sắp xếp (mặc định từ constant)
     * @param filter Bộ lọc nâng cao (có thể null)
     * @param search Từ khóa tìm kiếm toàn văn
     * @param all Nếu true thì lấy toàn bộ, không phân trang
     * @return ListResponse chứa các message đã tìm được
     */
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
