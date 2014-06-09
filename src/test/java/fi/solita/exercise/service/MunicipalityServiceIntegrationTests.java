package fi.solita.exercise.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.solita.exercise.Application;
import fi.solita.exercise.domain.Municipality;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class MunicipalityServiceIntegrationTests {

    @Autowired
    private MunicipalityService service;

    @Autowired
    TestDataService testDataService;

    @Test
    public void getAllMunicipalitiesTest() {
        Municipality municipality = testDataService.createDefaultMunicipality();
        assertEquals(1, service.getAllMunicipalities().size());
        assertEquals(municipality.getName(), service.getMunicipality(municipality.getId()).getName());
    }
}