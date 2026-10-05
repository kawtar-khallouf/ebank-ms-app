package stage.ifm.customerservice.service;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;
import stage.ifm.customerservice.entities.Customer;
import stage.ifm.customerservice.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @McpTool(description = "Get all customers")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @McpTool(description = "Find a customer by id")
    public Customer findCustomerById(@McpToolParam(description = "The customer id") Long id){
        return customerRepository.findById(id).orElseThrow(()->new RuntimeException("Customer not found!"));
    }
//    @McpTool(description = "Save a new customer")
//    public Customer saveCustomer(@McpToolParam(description = "The Customer to save(name,email)") Customer customer){
//        return customerRepository.save(customer);
//    }
@McpTool(description = "Save a new customer")
public Customer saveCustomer(
        @McpToolParam(description = "Customer name") String name,
        @McpToolParam(description = "Customer email") String email) {

    Customer customer = Customer.builder()
            .name(name)
            .email(email)
            .build();

    return customerRepository.save(customer);
}
}
