package com.example.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PassengerResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private List<Long> flights;
}
