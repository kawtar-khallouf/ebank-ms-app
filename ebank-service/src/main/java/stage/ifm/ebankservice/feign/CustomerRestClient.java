package stage.ifm.ebankservice.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import stage.ifm.ebankservice.model.Customer;

@FeignClient(name = "customer-service")
public interface CustomerRestClient {
    @GetMapping("/customers/{id}")
    @CircuitBreaker(name = "customerService", fallbackMethod = "getDefaultCustomer")
    Customer getCustomerById(@PathVariable Long id);

    default Customer getDefaultCustomer(Long id,Exception e){
        return new Customer(id,"Not available","Not available");
    }
}
