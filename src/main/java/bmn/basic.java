package bmn;

import configuration.hibernate;
import model.Stu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class basic {


    static List<String> list;
    static  Service service;
    static int status=9;
   static Scanner in =new Scanner(System.in);
   static PrintStream out;
    public static void main(String args[]){
//           hibernate config=new hibernate();
//       DAO dao=new DAO(config.sessionFactoryBean(config.dataSource(),config.hibernateProperties()));
//       service=new Service(dao);
        ApplicationContext context=new AnnotationConfigApplicationContext(hibernate.class);

        service=context.getBean(Service.class);
        out=context.getBean("printer",PrintStream.class);
        //context.getBean("resource",Properties.class).forEach((key,value)->{out.println(key+"   "+value);});
        list=context.getBean("text_Resource",List.class);
       // context.getResource("classpath:/properties/app.properties").getInputStream();
      /*  try(InputStream in=basic.class.getResourceAsStream("/properties/app.properties")){
            Properties map=new Properties();
            map.load(in);
            map.forEach((key,value)->out.println(key+"  "+value));
        }catch (IOException e){e.printStackTrace();}*/

W:while(true){
switch(status){
    case(9):
        printR();
        input();
        break;
    case(0):
            break W;
    case(-1):
        status=9;
        out.print("your entry is wrong , please try again");
        in.nextLine();
        break;
    case(1):
        doA();
        break;
    case(2):
        seeAll();
        break;
    case(3):
       delete();
        break;

    }


}in.close();
    }

static void printR(){
      list.forEach(out::println);
    }
static void input(){
    String inp=in.nextLine();
    try{
    status= Integer.parseInt(inp);
    }catch (NumberFormatException e){
        status=-1;
    }
}
static void doA() {
    out.print("enter student id:");
    String s = in.nextLine();
    int id;
    try {
        id = Integer.parseInt(s);
    } catch (NumberFormatException e) {
        status = -1;
        return;
    }
    out.print("enter student name:");
    String name = in.nextLine();
    out.print("enter student last name:");
    String Lname = in.nextLine();
    out.print("inter student Birthday(e.g. 1994 05 22):");
    String birthS=in.nextLine();
    Stu stu = new Stu(id, name, Lname,birthS);

    String s1=(Boolean)service.save(stu)?"succeed":"unsuccessful";
    out.println(s1);
    status=9;
}
static void seeAll(){
        List<Stu> list=service.getAll();
    for(Stu stu:list){
        out.print(stu+"\n");
        status=9;
    }
    }
    static  void delete(){
        out.print("inter student id:");
        int id;
       try{ id=Integer.parseInt(in.nextLine());
       }catch (NumberFormatException e){
           status=-1;
           return;
       }

       service.delete(id);
       status=9;
    }
}
