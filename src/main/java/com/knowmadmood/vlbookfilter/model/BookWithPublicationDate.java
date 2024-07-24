package com.knowmadmood.vlbookfilter.model;

import java.time.LocalDate;

public class BookWithPublicationDate extends Book{

    private String publicationDateStr;

    public BookWithPublicationDate(int id, String title, int pages, String summary, LocalDate publicationDate, Author author, String publicationDateStr){
        super(id, title, pages, summary, publicationDate, author);
        this.publicationDateStr = this.publicationDateStr;
    }

    public String getPublicationDateStr() {
        return publicationDateStr;
    }

    public void setPublicationDateStr(String publicationDateStr) {
        this.publicationDateStr = publicationDateStr;
    }

}
