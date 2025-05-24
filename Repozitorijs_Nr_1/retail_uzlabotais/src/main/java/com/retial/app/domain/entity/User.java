package com.retial.app.domain.entity;

import com.retial.app.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private UserType type;
    private Date startDate;

}
