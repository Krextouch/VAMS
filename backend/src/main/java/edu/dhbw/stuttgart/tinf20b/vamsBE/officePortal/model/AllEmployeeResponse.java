package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class AllEmployeeResponse {

    private List<AllEmployeeParam> employeeList = new ArrayList<>();

    public AllEmployeeResponse(List<AllEmployeeParam> employeeList) {
        this.employeeList = employeeList;
    }
}
