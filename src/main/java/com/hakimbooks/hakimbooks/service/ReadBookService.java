package com.hakimbooks.hakimbooks.service;

import com.hakimbooks.hakimbooks.exception.ResourceNotFoundException;
import com.hakimbooks.hakimbooks.model.Book;
import com.hakimbooks.hakimbooks.model.ReadBook;
import com.hakimbooks.hakimbooks.model.User;
import com.hakimbooks.hakimbooks.pojo.ReadBookRequestData;
import com.hakimbooks.hakimbooks.pojo.ReadBookResponse;
import com.hakimbooks.hakimbooks.pojo.UserResponse;
import com.hakimbooks.hakimbooks.repository.BookRepository;
import com.hakimbooks.hakimbooks.repository.ReadBookRepository;
import com.hakimbooks.hakimbooks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadBookService {
    private final ReadBookRepository readBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public ReadBookResponse createReadBook(ReadBookRequestData requestData){
        Book book=bookRepository.findById(requestData.getBookId())
                .orElseThrow(()->new ResourceNotFoundException("Book not found with id : "+requestData.getBookId()));
        User user=userRepository.findById(requestData.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : "+requestData.getUserId()));

        ReadBook readBook=new ReadBook();
        readBook.setReadPages(requestData.getReadPages());
        readBook.setUser(user);
        readBook.setBook(book);

        ReadBook savedReadBook = readBookRepository.save(readBook);

        UserResponse userResponse=modelMapper.map(user, UserResponse.class);

        return new ReadBookResponse(
                savedReadBook.getId(),
                userResponse,
                book.getId(),
                book.getNameInfo(),
                savedReadBook.getReadPages(),
                savedReadBook.getReadDate()
        );
    }

    public ReadBookResponse getReadBook(long id){
        ReadBook readBook = readBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadBook not found with id : " + id));

        UserResponse userResponse=modelMapper.map(readBook.getUser(), UserResponse.class);

        return new ReadBookResponse(
                readBook.getId(),
                userResponse,
                readBook.getBook().getId(),
                readBook.getBook().getNameInfo(),
                readBook.getReadPages(),
                readBook.getReadDate()
        );
    }

    public List<ReadBookResponse> getReadBookOfBook(long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id : " + bookId));

        UserResponse userResponse=modelMapper.map(book.getUser(), UserResponse.class);

        return book.getReadBooks().stream().map(readBook -> new ReadBookResponse(
                readBook.getId(),
                userResponse,
                book.getId(),
                book.getNameInfo(),
                readBook.getReadPages(),
                readBook.getReadDate()
        )).toList();
    }

    public List<ReadBookResponse> getReadBookOfUser(long userId){
        List<ReadBook> readBookList = readBookRepository.findAllByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("ReadBook not found with user_id : " + userId));

        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : "+userId));

        UserResponse userResponse=modelMapper.map(user, UserResponse.class);


        return readBookList.stream().map(readBook ->  new ReadBookResponse(
                readBook.getId(),
                userResponse,
                readBook.getBook().getId(),
                readBook.getBook().getNameInfo(),
                readBook.getReadPages(),
                readBook.getReadDate()
        )).toList();
    }

    public void deleteReadBook(long id){
        ReadBook readBook = readBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadBook not found with id : " + id));
        readBookRepository.delete(readBook);
    }
}
