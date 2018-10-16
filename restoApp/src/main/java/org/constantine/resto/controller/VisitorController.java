package org.constantine.resto.controller;

import org.constantine.resto.exception.CustomError;
import org.constantine.resto.model.SignInRequest;
import org.constantine.resto.model.Visitor;
import org.constantine.resto.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class VisitorController {

    @Autowired
    private VisitorRepository visitorRepository;

    @PostMapping("/visitors/signup")
    public ResponseEntity<?> signUp(
            @Valid @RequestBody Visitor visitor) {
        if(visitorRepository.existsByUsername(visitor.getUsername())) {
            return new ResponseEntity<>(new CustomError("Unable to create. A User with username " +
                    visitor.getUsername() + " already exist."), HttpStatus.CONFLICT);
        }
        Visitor client = visitorRepository.save(visitor);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/visitors/signin")
    public ResponseEntity<?> signIn(
            @Valid @RequestBody SignInRequest signInRequest) {
        Visitor client = visitorRepository.findByUsernameAndPassword(signInRequest.getUsername(), signInRequest.getPassword());
        if(client == null) {
            return new ResponseEntity<>(new CustomError("Invalid Credentials."), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

}
