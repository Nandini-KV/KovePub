package com.kognivera.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kognivera.messaging.MessagePublisher;
import com.kognivera.model.CreateUserMessage;
import com.kognivera.model.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserCreatedEventPublisher {

    private final MessagePublisher messagePublisher;

    @GetMapping("/publish/create_user_profile")
    public ResponseEntity<String> publishUserCreatedEvent() throws JsonProcessingException {

        String json = """
                {
                  "loginEmail": "test.test14@kv.com",
                  "prnId": "27108976",
                  "notificationEmail":"test.test14@kv.com",
                  "name": "Venkateshwarlu",
                  "motherLastName": "Cheedara",
                  "lastName": "Cheedara",
                  "gender": "M",
                  "dateOfBirth": "06/08/2000"
                }
                """;

        UserResponse userResponse = new ObjectMapper().readValue(json, UserResponse.class);

        CreateUserMessage createUserMessage = new CreateUserMessage(userResponse);



        messagePublisher.publish(createUserMessage);

        return new ResponseEntity<>("Published.", HttpStatus.OK);
    }
}
