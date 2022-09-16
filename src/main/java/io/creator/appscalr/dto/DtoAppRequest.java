package io.creator.appscalr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoAppRequest {
    private String appname;
    private String appdesc;
    private String appicon;
    // Notice that the following attribute is used as a page id when editing App and as a template id when creating it.
    private long templateid;
}
