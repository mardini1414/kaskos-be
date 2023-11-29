package mardini.project.kaskos.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashOutRequest {
    @Min(10000)
    @Max(1000000)
    private Integer nominal;
}
