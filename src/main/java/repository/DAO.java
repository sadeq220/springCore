package repository;

import model.Stu;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Repository
public class DAO {
    private SessionFactory factory;
    @Autowired
    public DAO(SessionFactory sessionFactory){
        this.factory=sessionFactory;
    }

    public List<Stu> getAll(){
        return factory.getCurrentSession().createQuery("from model.Stu E order by E.name").list();
    }
    public Stu find(int id){
//        TypedQuery<Stu> q= factory.getCurrentSession().createQuery("from Stu E where E.id=:ID",Stu.class);
//        q.setParameter("ID",id);
//        return q.getSingleResult();
        Session session=factory.getCurrentSession();
        return session.get(Stu.class,new Integer(id));
    }
    public void delete(int id){

        Session session=factory.getCurrentSession();
        Stu student=session.load(Stu.class,new Integer(id));
        session.delete(student);

        // factory.getCurrentSession().createQuery("delete from Stu E where E.id=?1").setParameter(1,id).executeUpdate();
    }
    public void update(Stu stu){

        factory.getCurrentSession().update(stu);
    }
    public Serializable saveStu(Stu student){

        Session session=factory.getCurrentSession();

       return session.save(student);
    }
}
