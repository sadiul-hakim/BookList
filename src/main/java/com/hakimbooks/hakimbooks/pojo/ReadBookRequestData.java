package com.hakimbooks.hakimbooks.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadBookRequestData {
    @NonNull
    private long userId;
    @NonNull
    private long bookId;
    @NonNull
    private int startPage;
    @NonNull
    private int endPage;
    @NonNull
    private boolean revise;
}
