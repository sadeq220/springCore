package aspects;


import model.Stu;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import service.Service;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Aspect
@Component
public class MyAspect {

    @Autowired
    @Qualifier(value = "printer")
    public PrintStream out;

    @AfterReturning(pointcut = "execution(* service.Service.*(..))")
    public void log(JoinPoint joinPoint){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("EEE, yyyy/MMM/dd");
        out.println(joinPoint.getSignature().getName()+"   executed in   "+ LocalDateTime.now().format(formatter));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Around(value = "execution(* service.Service.save(..))&&args(stu,..)")
    public Object check(ProceedingJoinPoint joinPoint, Stu stu){
       Service service= (Service) joinPoint.getTarget();
      if(service.find(stu.getId()) == null) {
          try {
              joinPoint.proceed();
              System.out.println("successfully saved");
              return true;
          } catch (Throwable throwable) {
              throwable.printStackTrace();
              return false;
          }
      }else {
          out.println("this ID was Already saved");
          return false;}
    }

}
