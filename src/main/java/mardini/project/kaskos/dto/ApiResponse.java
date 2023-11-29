package mardini.project.kaskos.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse {
    public Object data;
    public Object errors;
}
