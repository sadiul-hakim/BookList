package com.hakimbooks.hakimbooks.controller;

import com.hakimbooks.hakimbooks.pojo.ReadBookRequestData;
import com.hakimbooks.hakimbooks.pojo.ReadBookResponse;
import com.hakimbooks.hakimbooks.service.ReadBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readBook")
@RequiredArgsConstructor
public class ReadBookController {
    private final ReadBookService readBookService;

    @PostMapping("/post")
    public ResponseEntity<ReadBookResponse> createReadBook(@RequestBody ReadBookRequestData requestData){
        ReadBookResponse readBook = readBookService.createReadBook(requestData);
        return ResponseEntity.status(HttpStatus.CREATED).body(readBook);
    }

    @GetMapping("/{readBookId}")
    public ResponseEntity<ReadBookResponse> getReadBook(@PathVariable long readBookId){
        ReadBookResponse readBook = readBookService.getReadBook(readBookId);
        return ResponseEntity.ok(readBook);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReadBookResponse>> getReadBookOfBook(@PathVariable long bookId){
        List<ReadBookResponse> readBook = readBookService.getReadBookOfBook(bookId);
        return ResponseEntity.ok(readBook);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReadBookResponse>> getReadBookOfUser(@PathVariable long userId){
        List<ReadBookResponse> readBook = readBookService.getReadBookOfUser(userId);
        return ResponseEntity.ok(readBook);
    }
}
