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
        readBook.setStartPage(requestData.getStartPage());
        readBook.setEndPage(requestData.getEndPage());
        readBook.setRevise(requestData.isRevise());
        readBook.setUser(user);
        readBook.setBook(book);

        ReadBook savedReadBook = readBookRepository.save(readBook);

        return getResponse(savedReadBook);
    }

    public ReadBookResponse getReadBook(long id){
        ReadBook readBook = readBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadBook not found with id : " + id));

        return getResponse(readBook);
    }

    public List<ReadBookResponse> getReadBookOfBook(long bookId){
        List<ReadBook> readBook = readBookRepository.findAllByBookId(bookId);

        return readBook.stream().map(this::getResponse).toList();
    }

    public List<ReadBookResponse> getReadBookOfUser(long userId){
        List<ReadBook> readBookList = readBookRepository.findAllByUserId(userId);

        return readBookList.stream().map(this::getResponse).toList();
    }

    public void deleteReadBook(long id){
        ReadBook readBook = readBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadBook not found with id : " + id));
        readBookRepository.delete(readBook);
    }

    public ReadBookResponse getResponse(ReadBook readBook){
        UserResponse userResponse=modelMapper.map(readBook.getUser(), UserResponse.class);
        return new ReadBookResponse(
                readBook.getId(),
                userResponse,
                readBook.getBook().getId(),
                readBook.getBook().getNameInfo(),
                readBook.getStartPage(),
                readBook.getEndPage(),
                readBook.isRevise(),
                readBook.getReadDate()
        );
    }
}
