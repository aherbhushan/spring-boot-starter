package com.springstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    record NewCustomerRequest(
            Integer id,
            String name,
            String email,
            Integer age
    ){
    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest newCustomerRequest) {
        Customer customer = new Customer();
        customer.setAge(newCustomerRequest.age());
        customer.setName(newCustomerRequest.name());
        customer.setEmail(newCustomerRequest.email());

        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer customerId){
        customerRepository.deleteById(customerId);

    }

    @GetMapping("{customerId}")
    public Optional getCustomerById(@PathVariable("customerId") Integer id){
        return customerRepository.findById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@RequestBody NewCustomerRequest newCustomerRequest, @PathVariable("customerId") Integer id) {
        customerRepository.findById(id);
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(newCustomerRequest.name());
        customer.setEmail(newCustomerRequest.email());
        customer.setAge(newCustomerRequest.age());
        customerRepository.save(customer);
    }
}
