package fi.solita.exercise;

import fi.solita.exercise.DAO.DepartmentsRepository;
import fi.solita.exercise.domain.Department;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        DepartmentsRepository repository = context.getBean(DepartmentsRepository.class);

        repository.save(new Department(""));
//        repository.save(new Department("depa2"));

//        Iterable<Department> departments = repository.findAll();
//
//        System.out.println("Departments found with findAll():");
//        System.out.println("-------------------------------");
//        for (Department department : departments) {
//            System.out.println(department.getName());
//        }
//        System.out.println();

        context.close();
    }
}
