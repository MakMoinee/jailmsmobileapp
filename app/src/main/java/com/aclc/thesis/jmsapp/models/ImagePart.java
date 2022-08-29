package com.aclc.thesis.jmsapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImagePart {

    private String fileName;
    private byte[] content;
    private String type;
}
