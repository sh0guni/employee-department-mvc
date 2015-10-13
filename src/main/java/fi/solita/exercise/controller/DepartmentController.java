package fi.solita.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fi.solita.exercise.dao.DepartmentWithEmployeeCountDTO;
import fi.solita.exercise.service.DepartmentCreateDTO;
import fi.solita.exercise.service.DepartmentDTO;
import fi.solita.exercise.service.DepartmentService;
import fi.solita.exercise.service.DepartmentUpdateDTO;
import fi.solita.exercise.service.EmployeeService;

@SuppressWarnings("ALL")
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
    public List<DepartmentWithEmployeeCountDTO> getAllDepartments() {
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
    public ResponseEntity deleteDepartment(@PathVariable final long id) {
        try {
            departmentService.deleteDepartment(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>("Cannot remove department with employees", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleBadInput(final HttpMessageNotReadableException ex) throws Exception {
        Throwable cause = ex.getCause();
        cause.printStackTrace();
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
