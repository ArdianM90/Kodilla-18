package com.crud.tasks.domain;

import lombok.Getter;

import java.util.Date;

@Getter
public class Mail {
    public Mail(String mailTo, String mailToCc, String subject, String message) {
        this.mailTo = mailTo;
        this.mailToCc = mailToCc;
        this.sentDate = new Date();
        this.subject = subject;
        this.message = message;
    }
    public Mail(String mailTo, String mailToCc, Date sentDate, String subject, String message) {
        this.mailTo = mailTo;
        this.mailToCc = mailToCc;
        this.sentDate = sentDate;
        this.subject = subject;
        this.message = message;
    }

    private String mailTo;
    private String mailToCc;
    private Date sentDate;
    private String subject;
    private String message;
}
