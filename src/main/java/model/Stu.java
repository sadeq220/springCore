package model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Stu {
    @Id
    private int id;
    private String name;
    private  String Lname;
    @Basic
    private LocalDate birthday;

    public Stu(){}
    public Stu(int id,String name,String Lname,String s){
        this.id=id;
        this.name=name;
        this.Lname=Lname;
        setBirthday(s);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

public String toString(){
        return id +"  "+name+"         "+Lname+"   "+(birthday==null?"":birthday);
}

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy MM dd");
        this.birthday=LocalDate.parse(birthday,formatter);
    }
}
