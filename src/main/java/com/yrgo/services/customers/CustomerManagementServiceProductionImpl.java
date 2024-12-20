package com.yrgo.services.customers;

import com.yrgo.data.CustomerDao;
import com.yrgo.data.RecordNotFoundException;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("customerService")
public class CustomerManagementServiceProductionImpl implements CustomerManagementService {
    private CustomerDao customerDao;

    @Autowired
    public CustomerManagementServiceProductionImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void newCustomer(Customer newCustomer) {
        customerDao.create(newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException {
        try {
            customerDao.update(changedCustomer);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException("Customer not found for update", e);
        }
    }

    @Override
    public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException {
        try {
            customerDao.delete(oldCustomer);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException("Customer not found for deletion", e);
        }
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
        try {
            return customerDao.getById(customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId, e);
        }
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return customerDao.getByName(name);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
        try {
            return customerDao.getFullCustomerDetail(customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException("Customer not found with full detail for ID: " + customerId, e);
        }
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        try {
            customerDao.addCall(callDetails, customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException("Customer not found to record call with ID: " + customerId, e);
        }
    }
}
