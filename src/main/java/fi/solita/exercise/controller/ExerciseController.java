package fi.solita.exercise.controller;

import fi.solita.exercise.service.DepartmentDTO;
import fi.solita.exercise.service.DepartmentService;
import fi.solita.exercise.service.EmployeeDTO;
import fi.solita.exercise.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ExerciseController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @RequestMapping(method = RequestMethod.GET, value="/departments/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentByName(@PathVariable long id) {
        DepartmentDTO department = departmentService.getDepartment(id);

        if (department == null) {
            return new ResponseEntity<DepartmentDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DepartmentDTO>(department, HttpStatus.OK);
    }

    @RequestMapping(value="/departments", method = RequestMethod.POST)
    public ResponseEntity<DepartmentDTO>
    addDepartment(@RequestBody DepartmentDTO department,
                  UriComponentsBuilder builder) {
        DepartmentDTO newDepartment =
                departmentService.addDepartment(department.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/departments/{id}")
                        .buildAndExpand(newDepartment.getId()).toUri());

        return new ResponseEntity<DepartmentDTO>(
                newDepartment, headers,HttpStatus.CREATED);
    }

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
    addEmployee(@RequestBody EmployeeDTO employee,
                  UriComponentsBuilder builder) {
        EmployeeDTO newEmployee =
                employeeService.addEmployee(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getContractBeginDate(), employee.getDepartmentId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/employees/{id}")
                        .buildAndExpand(newEmployee.getId()).toUri()
        );

        return new ResponseEntity<EmployeeDTO>(
                newEmployee, headers,HttpStatus.CREATED);
    }

    @RequestMapping(value="/employees/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleBadInput(HttpMessageNotReadableException ex) throws Exception {
        Throwable cause = ex.getCause();
        cause.printStackTrace();
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}