package com.symbizsolutions.demo.controller;

import java.util.List;

import com.symbizsolutions.demo.entity.Customer;
import com.symbizsolutions.demo.exception.ResourceNotFoundException;
import com.symbizsolutions.demo.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepository repository;
    @GetMapping("/customers")
    List<Customer> list() {
        return repository.findAll();
    }

    @PostMapping(
            value = "/customer", consumes = "application/json", produces = "application/json")
    Customer save(@RequestBody Customer newCustomer) {
        return repository.save(newCustomer);
    }

    @GetMapping("/customer/{id}")
    Customer find(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @PutMapping("/customer/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable String id) {

        return repository.findById(id)
                .map(customer -> {
                    customer.setName(newCustomer.getName());
                    customer.setDob(newCustomer.getDob());
                    customer.setGender(newCustomer.getGender());
                    customer.setCountryCode(newCustomer.getCountryCode());
                    return repository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return repository.save(newCustomer);
                });
    }

    @DeleteMapping("/customer/{id}")
    void deleteCustomer(@PathVariable String id) {
        repository.deleteById(id);
    }
}
