package io.creator.appscalr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoAppResponse {
    private long app_id;
    private String app_name;
    private String app_desc;
    private String created_at;
    private long modifications;
    private String last_modified;
    private String app_icon_url;
}
