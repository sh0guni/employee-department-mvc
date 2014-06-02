package fi.solita.exercise.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import fi.solita.exercise.Application;
import org.dbunit.ext.h2.H2Connection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup(value="/municipalityServiceTestData.xml", type= DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value="/municipalityServiceTestData.xml", type= DatabaseOperation.DELETE_ALL)
public class MunicipalityServiceIntegrationTests {

    @Autowired
    private MunicipalityService service;

    @Test
    public void getAllMunicipalitiesTest() {
        assertThat(
                service.getAllMunicipalities().stream()
                        .map(x -> x.getName()).collect(Collectors.toList()),
                containsInAnyOrder("mun1", "mun2")
        );
    }
}