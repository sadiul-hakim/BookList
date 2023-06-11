package com.hakimbooks.hakimbooks.service;

import com.hakimbooks.hakimbooks.exception.ResourceNotFoundException;
import com.hakimbooks.hakimbooks.model.BookName;
import com.hakimbooks.hakimbooks.repository.BookNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookNameService {
    private final BookNameRepository bookNameRepository;
    public BookName createName(BookName bookName){
        return bookNameRepository.save(bookName);
    }

    public BookName getName(long nameId){
        return bookNameRepository.findById(nameId)
                .orElseThrow(()-> new ResourceNotFoundException("BookName not found with id : "+nameId));
    }

    public List<BookName> getAllByWriterName(String writerName){
        return bookNameRepository.findAllByWriterName(writerName);
    }

    public List<BookName> getAll(){
        return bookNameRepository.findAll();
    }

    public void deleteBookName(long nameId){
        BookName bookName = bookNameRepository.findById(nameId)
                .orElseThrow(() -> new ResourceNotFoundException("BookName not found with id : " + nameId));
        bookNameRepository.delete(bookName);
    }
}
