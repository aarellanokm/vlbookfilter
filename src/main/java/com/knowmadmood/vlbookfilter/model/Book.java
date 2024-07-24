package com.knowmadmood.vlbookfilter.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private int id;

    private String title;

    private int pages;

    private String summary;

    private LocalDate publicationDate;

    private Author author;

    @JsonProperty("publicationTimestamp")
    public void setPublicationTimestamp(Long publicationTimestamp){
        if (publicationTimestamp != null) {
            this.publicationDate = Instant.ofEpochSecond(publicationTimestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }
}