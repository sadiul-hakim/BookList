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

    public BookName getByName(String name){
        return bookNameRepository.findByBookName(name)
                .orElseThrow(()-> new ResourceNotFoundException("BookName not found with book name : "+name));
    }

    public List<BookName> getAllByWriterName(String writerName){
        return bookNameRepository.findAllByWriterName(writerName)
                .orElseThrow(()-> new ResourceNotFoundException("BookNames not found with writer name : "+writerName));
    }
}
