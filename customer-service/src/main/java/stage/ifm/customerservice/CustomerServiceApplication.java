package stage.ifm.customerservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import stage.ifm.customerservice.entities.Customer;
import stage.ifm.customerservice.repository.CustomerRepository;
import stage.ifm.customerservice.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerService customerService) {
        return args -> {
            List<String> names = List.of("Mohammed","Imane","Yassine");

            names.forEach(name->{
                customerService.saveCustomer( name,name+"@gmail.com");
            });
        };
    }

}
