package com.mygame.service.chat;

import com.mygame.dto.chat.MessageRequest;
import com.mygame.dto.chat.MessageResponse;
import com.mygame.service.CrudService;

public interface MessageService extends CrudService<Long, MessageRequest, MessageResponse> {}
