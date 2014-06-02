package fi.solita.exercise.controller;

import fi.solita.exercise.service.EmployeeCreateDTO;
import fi.solita.exercise.service.EmployeeDTO;
import fi.solita.exercise.service.EmployeeService;
import fi.solita.exercise.service.EmployeeUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value="/departments/{id}/employees", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDTO>> getDepartmentEmployees(@PathVariable long id) {
        List<EmployeeDTO> employees = employeeService.getEmployeesOfDepartment(id);

        if (employees == null) {
            return new ResponseEntity<List<EmployeeDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<EmployeeDTO>>(employees, HttpStatus.OK);
    }

    @RequestMapping(value="/employees", method = RequestMethod.POST)
    public ResponseEntity<EmployeeDTO>
    addEmployee(@RequestBody EmployeeCreateDTO employee,
                  UriComponentsBuilder builder) {
        EmployeeDTO newEmployee =
                employeeService.addEmployee(employee);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/employees/{id}")
                        .buildAndExpand(newEmployee.getId()).toUri()
        );

        return new ResponseEntity<EmployeeDTO>(
                newEmployee, headers,HttpStatus.CREATED);
    }

    @RequestMapping(value="/employees/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeUpdateDTO employee) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<EmployeeDTO>(updatedEmployee, HttpStatus.OK);
    }

    @RequestMapping(value="/employees/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="/employees/count", method = RequestMethod.GET)
    @ResponseBody public ResponseEntity<HashMap<String, Long>> findEmployeeCount() {
        HashMap<String, Long> employeeCount = new HashMap<String, Long>();
        employeeCount.put("count", employeeService.findEmployeeCount());
        return new ResponseEntity<HashMap<String, Long>>(employeeCount, HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleBadInput(HttpMessageNotReadableException ex) throws Exception {
        Throwable cause = ex.getCause();
        cause.printStackTrace();
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}