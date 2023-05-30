package com.hakimbooks.hakimbooks.service;

import com.hakimbooks.hakimbooks.exception.ResourceNotFoundException;
import com.hakimbooks.hakimbooks.model.User;
import com.hakimbooks.hakimbooks.pojo.UserRequestData;
import com.hakimbooks.hakimbooks.pojo.UserResponse;
import com.hakimbooks.hakimbooks.repository.UserRepository;
import com.hakimbooks.hakimbooks.security.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRequestData userRequest){
        User userModel = modelMapper.map(userRequest, User.class);
        userModel.setRole(Role.USER.getRole());
        userModel.setStartedAt(ZonedDateTime.now().toString());
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

        // todo: upload image and set photo property

        User savedUser = userRepository.save(userModel);

        return modelMapper.map(savedUser, UserResponse.class);
    }

    public UserResponse getUserById(long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse getUserByName(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email : " + email));
        return modelMapper.map(user, UserResponse.class);
    }

    public List<UserResponse> getUserList(int page,int userNum){
        PageRequest pageRequest = PageRequest.of(page,userNum, Sort.by("fullName"));
        List<User> pageList = userRepository.findAll(pageRequest).getContent();
        return pageList.stream().map(user->modelMapper.map(user, UserResponse.class)).toList();
    }

    public boolean deleteUser(long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
        userRepository.delete(user);
        return true;
    }
}
