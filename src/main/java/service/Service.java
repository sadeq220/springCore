package service;

import model.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import repository.DAO;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    private DAO dao;
    @Autowired
    public Service(DAO rep){
        dao=rep;
    }


    @Transactional
    public List<Stu> getAll(){
        return dao.getAll();
    }
    @Transactional
    public Stu find(int id){
       return dao.find(id);
    }
    @Transactional
    public Object save(Stu stu){
        dao.saveStu(stu);
        return new Object();
    }
    @Transactional
    public void delete(int id){
        dao.delete(id);
    }

}
