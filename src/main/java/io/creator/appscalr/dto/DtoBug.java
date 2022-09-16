package io.creator.appscalr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoBug {
    private String title;
    private String desc;
    private String bugtype;
    private String screenshoturl;
}
