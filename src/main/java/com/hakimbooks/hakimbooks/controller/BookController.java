package com.hakimbooks.hakimbooks.controller;

import com.hakimbooks.hakimbooks.model.Book;
import com.hakimbooks.hakimbooks.pojo.BookRequestData;
import com.hakimbooks.hakimbooks.pojo.BookResponse;
import com.hakimbooks.hakimbooks.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/post")
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequestData bookRequest){
        BookResponse savedBook = bookService.createBook(bookRequest, "");
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable long bookId){
        BookResponse book = bookService.getBook(bookId);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookResponse>> getBookList(){
        List<BookResponse> allBooks = bookService.getAllBooks();
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookResponse>> getAllBookOfUser(@PathVariable long userId){
        List<BookResponse> allBooksOfUser = bookService.getAllBooksOfUser(userId);
        return ResponseEntity.ok(allBooksOfUser);
    }
}
