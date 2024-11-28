package com.swissre;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Employee {

    private  Integer id;
    private  String firstName;
    private  String lastName;
    private  double salary;
    private  Integer managerId;

}
