package com.example.Backend.service;

import com.example.Backend.Dto.MessageDTO;
import com.example.Backend.model.Message;
import com.example.Backend.model.User;
import com.example.Backend.repository.MessageRepository;
import com.example.Backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserService userService, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Map<String, String>> createMessage(MessageDTO request) {
        Map<String, String> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null){
            response.put("message", "You are not logged in");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        if(userRepository.findByEmail(auth.getName()).isEmpty()){
            response.put("message", "You are not logged in");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        User owner = userRepository.findByEmail(auth.getName()).get();

        try{

            Date dateOfSendMessage = new Date();

            Message message = new Message();
            message.setTitle(request.getTitle());
            message.setContent(request.getContent());
            message.setOwner(owner);
            message.setDate(dateOfSendMessage);
            List<User> recipient = userRepository.findAllById(request.getRecipient());
            message.setRecipient(recipient);
            messageRepository.save(message);
            System.out.println(request.getTitle());

            response.put("message", "Message created");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
