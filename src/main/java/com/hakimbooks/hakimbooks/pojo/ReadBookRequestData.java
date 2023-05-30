package com.hakimbooks.hakimbooks.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadBookRequestData {
    private long userId;
    private long bookId;
    private int readPages;
}
