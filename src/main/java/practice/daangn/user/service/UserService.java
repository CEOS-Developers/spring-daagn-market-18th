package practice.daangn.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import practice.daangn.domain.User;
import practice.daangn.repository.UserRepository;
import practice.daangn.user.dto.UserRequestDto;
import practice.daangn.user.dto.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    @Transactional
    public Long saveUser(UserRequestDto dto){
        User user = dto.toEntity();
        userRepository.save(user);

        return user.getId();
    }

    public List<UserResponseDto> findAllUSer(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> findUserByEmail(String email){
        List<User> users = userRepository.findByEmailContaining(email);
        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다.");
        }
    }
}
