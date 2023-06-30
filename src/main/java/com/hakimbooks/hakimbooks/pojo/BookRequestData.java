package com.hakimbooks.hakimbooks.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookRequestData {
    @NonNull
    @NotEmpty
    private String bookName;
    @NonNull
    @NotEmpty
    private String writerName;
    @NonNull
    private int totalPages;
    @NonNull
    private long userId;
    @NonNull
    private long categoryId;
}
