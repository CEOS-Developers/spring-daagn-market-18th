package practice.daangn.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
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

    public List<UserResponseDto> findAllUSer(int pageNo){
//        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map(UserResponseDto::from)
//                .collect(Collectors.toList()); // 수정 허용, null 값 허용
        Pageable pageable = PageRequest.of(pageNo,2, Sort.by(Sort.Direction.ASC, "created"));
        Page<UserResponseDto> page = userRepository.findAll(pageable).map(UserResponseDto::from);

        return page.getContent();
    }

    public List<UserResponseDto> findUserByWord(String word){
        List<User> users = userRepository.findByEmailContaining(word);
        return users.stream()
                .map(UserResponseDto::from)
                .toList(); // 수정 불가, null 값 허용
    }

    @Transactional
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
