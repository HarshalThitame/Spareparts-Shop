package com.sparesparts.config.mail;

import lombok.Data;

@Data
public class EmailData {

    private String to;
    private String subject;
    private String body;

}
