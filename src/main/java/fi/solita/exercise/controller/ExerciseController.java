package fi.solita.exercise.controller;

import fi.solita.exercise.service.DepartmentDTO;
import fi.solita.exercise.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody String name, UriComponentsBuilder builder) {
        DepartmentDTO department = departmentService.addDepartment(name);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/departments/{id}")
                        .buildAndExpand(department.getId()).toUri());

        return new ResponseEntity<DepartmentDTO>(department, headers, HttpStatus.CREATED);
    }
}