package fi.solita.exercise.controller;

import fi.solita.exercise.service.*;
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
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable long id) {
        DepartmentDTO department = departmentService.getDepartment(id);

        if (department == null) {
            return new ResponseEntity<DepartmentDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DepartmentDTO>(department, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DepartmentDTO>
    addDepartment(@RequestBody DepartmentCreateDTO department,
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

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<DepartmentDTO> updateDepartment(@RequestBody DepartmentUpdateDTO department) {
        DepartmentDTO updatedDepartment = departmentService.updateDepartment(department);
        return new ResponseEntity<DepartmentDTO>(updatedDepartment, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDepartment(@PathVariable long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleBadInput(HttpMessageNotReadableException ex) throws Exception {
        Throwable cause = ex.getCause();
        cause.printStackTrace();
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}