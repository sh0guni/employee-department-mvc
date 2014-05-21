package fi.solita.exercise.controller;

import fi.solita.exercise.service.DepartmentDTO;
import fi.solita.exercise.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ExerciseController {

    @Autowired
    private DepartmentService departmentService;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleBadInput(HttpMessageNotReadableException ex) throws Exception {
        Throwable cause = ex.getCause();
        cause.printStackTrace();
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}