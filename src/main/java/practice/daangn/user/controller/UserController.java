package practice.daangn.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.daangn.user.dto.UserRequestDto;
import practice.daangn.user.service.UserService;

@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity saveUser(@Valid @RequestBody UserRequestDto dto){
        Long id = userService.saveUser(dto);
        return new ResponseEntity(id,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findUsers(){
        return new ResponseEntity(userService.findAllUSer(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity findUserByEmail(@PathVariable String email){
        return new ResponseEntity(userService.findUserByEmail(email), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
