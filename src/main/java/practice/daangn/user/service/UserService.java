package practice.daangn.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import practice.daangn.domain.User;
import practice.daangn.global.TokenProvider;
import practice.daangn.repository.UserRepository;
import practice.daangn.user.dto.response.TokenResponseDto;
import practice.daangn.user.dto.request.UserSignInRequestDto;
import practice.daangn.user.dto.request.UserSignUpRequestDto;
import practice.daangn.user.dto.response.UserResponseDto;


import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    public Long signUp(UserSignUpRequestDto requestDto) throws Exception {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 이메일입니다"); //이메일 중복 시 400 에러 반환
        }

        User user = requestDto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        userRepository.save(user);

        return user.getId();
    }

    public TokenResponseDto signIn(UserSignInRequestDto requestDto) throws Exception {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"잘못된 이메일입니다"));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다");
        }

        // 원본에서는 user가 userdetail 상속
        String accessToken = tokenProvider.createAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = tokenProvider.createRefreshToken();

        // refresh token redis에 저장

        return TokenResponseDto.builder()
                .grantType("Bearer")
                .jwtAccessToken(accessToken)
                .jwtRefreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getMyInfo(User user){
        System.out.println("확인");
        if(user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 정보가 없습니다."); //없어도 서비스 단까지 안 오지 않나?
        return UserResponseDto.from(user);
    }











    /**
     * week 3
     * crud api 개발 과제
     */

    public Long saveUser(UserSignUpRequestDto dto){
        User user = dto.toEntity();
        userRepository.save(user);

        return user.getId();
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUSer(int pageNo){
//        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map(UserResponseDto::from)
//                .collect(Collectors.toList()); // 수정 허용, null 값 허용
        Pageable pageable = PageRequest.of(pageNo,2, Sort.by(Sort.Direction.ASC, "created"));
        Page<UserResponseDto> page = userRepository.findAll(pageable).map(UserResponseDto::from);

        return page.getContent();
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findUserByWord(String word){
        List<User> users = userRepository.findByEmailContaining(word);
        return users.stream()
                .map(UserResponseDto::from)
                .toList(); // 수정 불가, null 값 허용
    }



    public void deleteUser(Long id){
//        if(userRepository.existsById(id)){
//            userRepository.deleteById(id);
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다.");
//        }
        userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."));

        userRepository.deleteById(id);

    }
}
