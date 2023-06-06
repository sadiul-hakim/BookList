package com.hakimbooks.hakimbooks.service;

import com.hakimbooks.hakimbooks.exception.ResourceNotFoundException;
import com.hakimbooks.hakimbooks.model.Book;
import com.hakimbooks.hakimbooks.model.BookName;
import com.hakimbooks.hakimbooks.model.Category;
import com.hakimbooks.hakimbooks.model.User;
import com.hakimbooks.hakimbooks.pojo.BookRequestData;
import com.hakimbooks.hakimbooks.pojo.BookResponse;
import com.hakimbooks.hakimbooks.pojo.ReadBookResponse;
import com.hakimbooks.hakimbooks.pojo.UserResponse;
import com.hakimbooks.hakimbooks.repository.BookNameRepository;
import com.hakimbooks.hakimbooks.repository.BookRepository;
import com.hakimbooks.hakimbooks.repository.CategoryRepository;
import com.hakimbooks.hakimbooks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookNameRepository bookNameRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public BookResponse createBook(BookRequestData bookRequestData,String photo){
        Book book=new Book();

        User user= userRepository.findById(bookRequestData.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : "+bookRequestData.getUserId()));
        Category category= categoryRepository.findById(bookRequestData.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category not found with id : "+bookRequestData.getCategoryId()));
        BookName bookName=bookNameRepository.findById(bookRequestData.getBookNameId())
                .orElseThrow(()-> new ResourceNotFoundException("BookName not found with id : "+bookRequestData.getBookNameId()));

        book.setUser(user);
        book.setNameInfo(bookName);
        book.setCategory(category);
        book.setTotalPages(bookRequestData.getTotalPages());
        book.setPhoto(photo);


        Book savedBook = bookRepository.save(book);

        UserResponse userResponse = modelMapper.map(savedBook.getUser(), UserResponse.class);
        List<ReadBookResponse> readBookResponsesList = mapReadBookResponse(savedBook);


        return new BookResponse(
                savedBook.getId(),
                savedBook.getNameInfo(),
                savedBook.getPhoto(),
                readBookResponsesList,
                userResponse,
                savedBook.getCategory()
        );
    }

    public BookResponse getBook(long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id : " + bookId));

        UserResponse userResponse = modelMapper.map(book.getUser(), UserResponse.class);
        List<ReadBookResponse> readBookResponsesList = mapReadBookResponse(book);

        return new BookResponse(
                book.getId(),
                book.getNameInfo(),
                book.getPhoto(),
                readBookResponsesList,
                userResponse,
                book.getCategory()
        );
    }

    public List<BookResponse> getAllBooks(){
        List<BookResponse> responseList=new ArrayList<>();
        List<Book> bookList = bookRepository.findAll();
        for(Book book:bookList){
            UserResponse userResponse = modelMapper.map(book.getUser(), UserResponse.class);
            List<ReadBookResponse> readBookResponsesList = mapReadBookResponse(book);

            responseList.add(
                    new BookResponse(
                            book.getId(),
                            book.getNameInfo(),
                            book.getPhoto(),
                            readBookResponsesList,
                            userResponse,
                            book.getCategory()
                    )
            );
        }

        return responseList;
    }

    public List<BookResponse> getAllBooksOfUser(long userId){
        List<BookResponse> responseList=new ArrayList<>();
        List<Book> bookList = bookRepository.findAllByUserId(userId);
        for(Book book:bookList){
            UserResponse userResponse = modelMapper.map(book.getUser(), UserResponse.class);
            List<ReadBookResponse> readBookResponsesList = mapReadBookResponse(book);
            responseList.add(
                    new BookResponse(
                            book.getId(),
                            book.getNameInfo(),
                            book.getPhoto(),
                            readBookResponsesList,
                            userResponse,
                            book.getCategory()
                    )
            );
        }

        return responseList;
    }

    public void deleteBook(long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id : " + bookId));
        bookRepository.delete(book);
    }

    private List<ReadBookResponse> mapReadBookResponse(Book book){
        return book.getReadBooks().stream().map(read -> {
            ReadBookResponse readBookResponse = modelMapper.map(read, ReadBookResponse.class);
            readBookResponse.setBookId(read.getId());
            readBookResponse.setBookName(book.getNameInfo());
            return readBookResponse;
        }).toList();
    }
}
