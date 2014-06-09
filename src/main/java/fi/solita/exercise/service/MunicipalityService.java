package fi.solita.exercise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.solita.exercise.dao.MunicipalityRepository;
import fi.solita.exercise.domain.Municipality;
import fi.solita.exercise.util.DtoFactory;

@Service
public class MunicipalityService {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private DtoFactory municipalityDtoFactory;

    @Transactional(readOnly = true)
    public MunicipalityDTO getMunicipality(final long id) {
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
