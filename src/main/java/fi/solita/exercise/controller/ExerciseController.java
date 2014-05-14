package fi.solita.exercise.controller;

import fi.solita.exercise.DTO.DepartmentDTO;
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
    DepartmentService departmentService;

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @RequestMapping(method = RequestMethod.GET, value="/departments/{name}")
    public ResponseEntity<DepartmentDTO> getDepartmentByName(@PathVariable String name) {
        DepartmentDTO department = departmentService.getDepartment(name);

        if (department == null) {
            return new ResponseEntity<DepartmentDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DepartmentDTO>(department, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody String name, UriComponentsBuilder builder) {
        DepartmentDTO department = departmentService.addDepartment(name);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/departments/{name}")
                        .buildAndExpand(name).toUri());

        return new ResponseEntity<DepartmentDTO>(department, headers, HttpStatus.CREATED);
    }
}