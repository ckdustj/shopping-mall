package com.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString(exclude = "data")
@NoArgsConstructor
public class ImageFileDTO {
    private int no;
    private byte[] data;
    private MultipartFile file;
    private String originalFileName;
    private String savedFileName;
}
