package com.example.Backend.controller;

import com.example.Backend.Dto.MessageDTO;
import com.example.Backend.model.Message;
import com.example.Backend.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/createMessage")
    public ResponseEntity<Map<String, String>> createMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.createMessage(messageDTO);
    }

}
