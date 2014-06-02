package fi.solita.exercise.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fi.solita.exercise.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("/controllerTestData.xml")
public class ExerciseControllerIntegrationTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                        .webAppContextSetup(this.webApplicationContext)
                        .build();
    }

    @Test
    public void addNewDepartmentTest() throws Exception {
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
        String testEmployee = "{ \"firstName\": \"Simo\", \"lastName\": \"Solita\", \"email\": \"simo@solita.fi\", \"firstName\": \"Simo\", \"contractBeginDate\":0, \"departmentId\": 100, \"municipalityId\": 100 }";
        this.mockMvc.perform(
                post("/employees")
                        .content(testEmployee)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(
                get("/departments/100/employees")
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