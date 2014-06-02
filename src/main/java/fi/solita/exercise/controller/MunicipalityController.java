package fi.solita.exercise.controller;

import fi.solita.exercise.service.MunicipalityDTO;
import fi.solita.exercise.service.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("municipalities")
public class MunicipalityController {

    @Autowired
    private MunicipalityService municipalityService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<MunicipalityDTO> getAllmunicipalities() {
        return municipalityService.getAllMunicipalities();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    public ResponseEntity<MunicipalityDTO> getMunicipalities(@PathVariable long id) {
        MunicipalityDTO municipality = municipalityService.getMunicipality(id);

        if (municipality == null) {
            return new ResponseEntity<MunicipalityDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<MunicipalityDTO>(municipality, HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleBadInput(HttpMessageNotReadableException ex) throws Exception {
        Throwable cause = ex.getCause();
        cause.printStackTrace();
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}