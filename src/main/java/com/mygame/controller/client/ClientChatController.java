package com.mygame.controller.client;

import com.mygame.constant.AppConstants;
import com.mygame.dto.chat.ClientRoomExistenceResponse;
import com.mygame.dto.chat.RoomResponse;
import com.mygame.entity.authentication.User;
import com.mygame.entity.chat.Message;
import com.mygame.entity.chat.Room;
import com.mygame.mapper.chat.MessageMapper;
import com.mygame.mapper.chat.RoomMapper;
import com.mygame.repository.authentication.UserRepository;
import com.mygame.repository.chat.MessageRepository;
import com.mygame.repository.chat.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client-api/chat")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ClientChatController {

    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private RoomMapper roomMapper;
    private MessageRepository messageRepository;
    private MessageMapper messageMapper;

    // API: Lấy phòng chat hiện tại của user đang đăng nhập (nếu có)
    @GetMapping("/get-room")
    public ResponseEntity<ClientRoomExistenceResponse> getRoom(Authentication authentication) {
        // Lấy username của người dùng hiện tại từ session (Spring Security)
        String username = authentication.getName();

        // Tìm phòng chat theo username → nếu có thì map sang DTO response
        RoomResponse roomResponse = roomRepository.findByUserUsername(username)
                .map(roomMapper::entityToResponse)
                .orElse(null);
        // Khởi tạo response trả về cho client
        var clientRoomExistenceResponse = new ClientRoomExistenceResponse();
        // Gán giá trị kiểm tra xem phòng có tồn tại không
        clientRoomExistenceResponse.setRoomExistence(roomResponse != null);
        clientRoomExistenceResponse.setRoomResponse(roomResponse);
        // Nếu có phòng → lấy 20 tin nhắn mới nhất, sắp xếp tăng dần theo ID
        clientRoomExistenceResponse.setRoomRecentMessages(
                roomResponse != null
                        ? messageMapper.entityToResponse(
                        messageRepository
                                .findByRoomId(
                                        roomResponse.getId(),
                                        // Lấy 20 tin nhắn mới nhất theo id giảm dần
                                        PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id")))
                                .stream()
                                .sorted(Comparator.comparing(Message::getId))
                                .collect(Collectors.toList()))// Nếu không có phòng → trả về list rỗng
                        : Collections.emptyList());

        return ResponseEntity.status(HttpStatus.OK).body(clientRoomExistenceResponse);
    }

    // API: Tạo mới phòng chat cho user hiện tại
    @PostMapping("/create-room")
    public ResponseEntity<RoomResponse> createRoom(Authentication authentication) {
        // Lấy username của người dùng hiện tại
        String username = authentication.getName();

        // Tìm user từ database, nếu không có → ném lỗi UsernameNotFoundException
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // Tạo mới một Room gắn với user đó
        Room room = new Room();
        room.setName(user.getFullname());
        room.setUser(user);

        Room roomAfterSave = roomRepository.save(room);
        // Trả về RoomResponse sau khi tạo phòng thành công
        return ResponseEntity.status(HttpStatus.OK).body(roomMapper.entityToResponse(roomAfterSave));
    }

}
