package com.yoavmorahg.learner_app.controller;

import com.yoavmorahg.learner_app.model.UserDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {

    public UserDTO getUser(Long id) {
        return null;
    }
}
