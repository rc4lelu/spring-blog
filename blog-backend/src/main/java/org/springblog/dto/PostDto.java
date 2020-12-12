package org.springblog.dto;

import lombok.Data;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private String username;

}
