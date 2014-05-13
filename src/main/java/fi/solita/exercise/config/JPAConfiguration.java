package fi.solita.exercise.config;

import fi.solita.exercise.DAO.DepartmentsRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@EnableJpaRepositories(basePackages = "fi.solita.exercise",
//    includeFilters = @ComponentScan.Filter(value = {DepartmentsRepository.class},
//    type = FilterType.ASSIGNABLE_TYPE))
//@EnableTransactionManagement
//@EnableAutoConfiguration
public class JPAConfiguration {
//
//    @Bean
//    public BasicDataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/harjoitus");
//        dataSource.setUsername("harkka");
//        dataSource.setPassword("harkka123");
//
//        return dataSource;
//    }
//
//    @Bean
//    public EntityManagerFactory entityManagerFactory() throws SQLException {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("fi.solita.exercise.domain");
//        factory.setDataSource(dataSource());
//        factory.afterPropertiesSet();
//
//        return factory.getObject();
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() throws SQLException {
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory());
//        return txManager;
//    }
//
//    @Bean
//    public HibernateExceptionTranslator hibernateExceptionTranslator() {
//        return new HibernateExceptionTranslator();
//    }

}
