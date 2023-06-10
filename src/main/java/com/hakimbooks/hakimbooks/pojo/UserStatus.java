package com.hakimbooks.hakimbooks.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus {
    private long userId;
    private int totalBook;
    private int totalReadBook;
    private int totalReadingBook;
    private int totalUnReadBook;
}
