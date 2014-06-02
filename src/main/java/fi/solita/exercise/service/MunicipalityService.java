package fi.solita.exercise.service;

import fi.solita.exercise.dao.MunicipalityRepository;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Municipality;
import fi.solita.exercise.util.DtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MunicipalityService {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private DtoFactory municipalityDtoFactory;

    @Transactional
    public MunicipalityDTO addMunicipality(String name) {
        Municipality municipality = new Municipality((name));
        municipalityRepository.save(municipality);
        return municipalityDtoFactory.createMunicipality(municipality);
    }

    @Transactional(readOnly = true)
    public MunicipalityDTO getMunicipality(long id) {
        Municipality municipality = municipalityRepository.getOne(id);
        return municipalityDtoFactory.createMunicipality(municipality);
    }

    @Transactional(readOnly = true)
    public List<MunicipalityDTO> getAllMunicipalities() {
        List<MunicipalityDTO> municipalities = new ArrayList<>();
        municipalityRepository.findAll()
                             .forEach(x -> municipalities.add(municipalityDtoFactory
                                     .createMunicipality(x)));
        return municipalities;
    }
}