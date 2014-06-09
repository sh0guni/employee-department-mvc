package fi.solita.exercise.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fi.solita.exercise.Application;
import fi.solita.exercise.dao.EmployeeRepository;
import fi.solita.exercise.service.TestDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ExerciseControllerIntegrationTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TestDataService testDataService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                        .webAppContextSetup(this.webApplicationContext)
                        .build();
    }

    @After
    @Autowired
    public void tearDown() {
        testDataService.clearDatabase();
    }

    @Test
    public void addNewDepartmentTest() throws Exception {
        testDataService.createDefaultDepartment();
        String testDepartment = "{ \"name\": \"TestDepartment\" }";
        this.mockMvc.perform(
                post("/departments")
                        .content(testDepartment)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(
                get("/departments")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("TestDepartment")));
    }

    @Test
    public void addEmployeeToDepartmentTest() throws Exception {
        Long departmentId = testDataService.createDefaultDepartment().getId();
        Long municipalityId = testDataService.createDefaultMunicipality().getId();
        String testEmployee = "{ \"firstName\": \"Simo\", \"lastName\": \"Solita\"," +
                "\"email\": \"simo@solita.fi\", \"firstName\": \"Simo\", \"contractBeginDate\":0," +
                "\"departmentId\": " + departmentId + ", \"municipalityId\": " + municipalityId + " }";
        this.mockMvc.perform(
                post("/employees")
                        .content(testEmployee)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(
                get("/departments/" + departmentId + "/employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("Simo")))
                .andExpect(jsonPath("$[0].lastName", is("Solita")))
                .andExpect(jsonPath("$[0].email", is("simo@solita.fi")))
                .andExpect(jsonPath("$[0].contractBeginDate", is(0)));
    }
}