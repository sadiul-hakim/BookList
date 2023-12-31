package com.hakimbooks.hakimbooks.controller;

import com.hakimbooks.hakimbooks.model.BookName;
import com.hakimbooks.hakimbooks.pojo.ApiResponse;
import com.hakimbooks.hakimbooks.service.BookNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookName")
@RequiredArgsConstructor
public class BookNameController {
    private final BookNameService bookNameService;

    @PostMapping("/post")
    public ResponseEntity<BookName> createBookName(@RequestBody BookName bookName){
        BookName savedBook = bookNameService.createName(bookName);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping("/{nameId}")
    public ResponseEntity<BookName> getBookName(@PathVariable long nameId){
        BookName name = bookNameService.getName(nameId);
        return ResponseEntity.ok(name);
    }

    @GetMapping("/writer")
    public ResponseEntity<List<BookName>> getALlByWriterName(@RequestParam String writerName){
        List<BookName> nameList = bookNameService.getAllByWriterName(writerName);
        return ResponseEntity.ok(nameList);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookName>> getALl(){
        List<BookName> nameList = bookNameService.getAll();
        return ResponseEntity.ok(nameList);
    }

    @DeleteMapping("/{nameId}")
    public ResponseEntity<ApiResponse> deleteBookName(@PathVariable long nameId){
        bookNameService.deleteBookName(nameId);
        return ResponseEntity
                .ok(new ApiResponse(String.format("BookName %d is deleted successfully.",nameId),true));
    }
}
