package com.hakimbooks.hakimbooks.service;

import com.hakimbooks.hakimbooks.exception.ResourceNotFoundException;
import com.hakimbooks.hakimbooks.model.ReadBook;
import com.hakimbooks.hakimbooks.model.User;
import com.hakimbooks.hakimbooks.pojo.*;
import com.hakimbooks.hakimbooks.repository.UserRepository;
import com.hakimbooks.hakimbooks.security.Role;
import com.hakimbooks.hakimbooks.utility.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookService bookService;
    private final ReadBookService readBookService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

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

    public UserResponse validateToken(AuthResponse auth){
        String token=auth.getToken();
        String username = jwtHelper.extractUsername(token);
        if(username == null || username.isEmpty() ) return null;
        User userDetails = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));
        if(!jwtHelper.isValidToken(token,userDetails)){
            return null;
        }
        return modelMapper.map(userDetails, UserResponse.class);
    }

    public UserStatus userStatus(long userId){
        UserStatus status=new UserStatus();

        List<BookResponse> allBooksOfUser = bookService.getAllBooksOfUser(userId);
        allBooksOfUser.forEach(book->{
            List<ReadBookResponse> readBookList = readBookService.getReadBookOfBook(book.getId());
            if(readBookList.isEmpty()) status.setTotalUnReadBook(status.getTotalUnReadBook()+1);
            else{
                int totalReadPages = readBookList.stream().map(ReadBookResponse::getReadPages).mapToInt(Integer::intValue).sum();
                if(book.getTotalPages() == totalReadPages){
                    status.setTotalReadBook(status.getTotalReadBook()+1);
                }else{
                    status.setTotalUnReadBook(status.getTotalUnReadBook()+1);
                }
            }
        });

        status.setUserId(userId);
        status.setTotalBook(allBooksOfUser.size());

        return status;
    }
}
