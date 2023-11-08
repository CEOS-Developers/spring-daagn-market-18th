package practice.daangn.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.daangn.user.dto.TokenResponseDto;
import practice.daangn.user.dto.UserSignInRequestDto;
import practice.daangn.user.dto.UserSignUpRequestDto;
import practice.daangn.user.service.UserService;

@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param requestDto
     * @return
     * @throws Exception
     */
    @PostMapping("/signup")
    public ResponseEntity signUp(@Valid @RequestBody UserSignUpRequestDto requestDto) throws Exception {
        Long id = userService.signUp(requestDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 로그인
     * @param requestDto
     * @return responseDto
     * @throws Exception
     */
    @PostMapping("/signin")
    public ResponseEntity singIn(@Valid @RequestBody UserSignInRequestDto requestDto) throws Exception {
        TokenResponseDto responseDto = userService.signIn(requestDto);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }


    /**
     * week 3
     * crud api 구현 과제
     */
    @PostMapping
    public ResponseEntity saveUser(@Valid @RequestBody UserSignUpRequestDto dto){
        Long id = userService.saveUser(dto);
        return new ResponseEntity(id,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findUsers(@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo){
        return new ResponseEntity(userService.findAllUSer(pageNo), HttpStatus.OK);
    }

    @GetMapping("/{word}")
    public ResponseEntity findUserByWord(@PathVariable String word){
        return new ResponseEntity(userService.findUserByWord(word), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
