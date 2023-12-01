package com.sparta.letstodogo;

import com.fasterxml.jackson.annotation.*;
import java.time.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDto {

    private String msg;
    private Integer statusCode;

}
