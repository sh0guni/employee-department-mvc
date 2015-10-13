package fi.solita.exercise.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentRepositoryImpl implements DepartmentRepositoryCustom {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<DepartmentWithEmployeeCountDTO> findAllWithEmployeeCount() {
        EntityManager em = entityManagerFactory.createEntityManager();

        Session session = em.unwrap(Session.class);
        String sql = "select department.id as id, department.name, count(Employee.department) as employeeCount " +
                "from department left join employee on department.id = employee.department " +
                "group by department.name, department.id";
        SQLQuery query = session.createSQLQuery(sql)
                .addScalar("id", StandardBasicTypes.INTEGER)
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("employeeCount", StandardBasicTypes.INTEGER);

        query.setResultTransformer(Transformers.aliasToBean(DepartmentWithEmployeeCountDTO.class));
        return query.list();
    }
}
