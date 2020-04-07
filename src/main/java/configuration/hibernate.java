package configuration;
import model.Stu;
import org.apache.commons.dbcp2.BasicDataSource;
//import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableTransactionManagement
@ComponentScan(value = {"service","repository","aspects"})
@EnableAspectJAutoProxy
@PropertySource("classpath:/properties/app.properties")
public class hibernate {
    @Autowired
    private org.springframework.core.env.Environment environment;
    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource=new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("DB.Driver"));
        dataSource.setUrl(environment.getRequiredProperty("DB.url"));
        dataSource.setUsername(environment.getRequiredProperty("DB.username"));
        dataSource.setPassword(environment.getProperty("DB.password"));
        dataSource.setInitialSize(1);
        dataSource.setMaxTotal(5);
        dataSource.setMaxIdle(5);
        dataSource.setMaxWaitMillis(10999);
        return  dataSource;
    }
    @Bean(name = {"properties"})
    public Properties hibernateProperties(){
        Properties property=new Properties();
        property.setProperty(Environment.DIALECT,environment.getRequiredProperty("Hibernate.dialect"));
       // property.put("javax.persistence.validation.mode","none");
       // property.setProperty(Environment.URL,"jdbc:mysql://localhost:3306/university");
       // property.setProperty(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
       // property.setProperty(Environment.USER,"root");
      //  property.setProperty(Environment.PASS,"Rootpassword");
      // property.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
        property.setProperty(Environment.HBM2DDL_AUTO,"update");
        return property;
    }
  @Bean
    // @Qualifier(value = "properties")
    public LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource,Properties properties){

//      org.hibernate.cfg.Configuration configuration=new org.hibernate.cfg.Configuration();
//        configuration.setProperties(properties);
//        configuration.addAnnotatedClass(Stu.class);
    LocalSessionFactoryBean sessionFactoryBean=new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setAnnotatedClasses(Stu.class);
        sessionFactoryBean.setHibernateProperties(properties);
        // sessionFactoryBean.setConfigLocation( "classpath:configuration.hibernate.cfg.xml");
        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager(LocalSessionFactoryBean sessionFactory){
        HibernateTransactionManager transactionManager=new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory.getObject());

        return transactionManager;

    }
    @Bean
    public PrintStream printer(){
        return System.out;
    }

    @Bean(name = "resource")
    public Properties getResource(){
        Properties properties=new Properties();
        try(InputStream stream=new ClassPathResource("/properties/app.properties").getInputStream()){
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
    @Bean(name = "text_Resource")
    public List<String> getText(){
        List<String> list = null;
        try(InputStream in=new ClassPathResource("/texts/app.txt").getInputStream()){
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            list=reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
