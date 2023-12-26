package com.shop.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SnsInfoDTO {
    private String id;
    private String clientName;
    private LocalDateTime connectDate;
    private Map<String, Object> attributes;
}
