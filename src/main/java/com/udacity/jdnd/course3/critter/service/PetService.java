package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Long create(Pet pet, Long customerID){
        Customer customer = customerRepository.getOne(customerID);
        pet.setCustomer(customer);
        petRepository.save(pet);
        List<Pet> petList = customer.getPets();
        if (Objects.isNull(petList)){
            petList = new ArrayList<>();
        }
        petList.add(pet);
        customer.setPets(petList);
        customerRepository.save(customer);
        return pet.getId();
    }


    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> GetPetsByCustomer(Long customerId){
        return petRepository.findPetByCustomerId(customerId);
    }

    public Pet getPetById(Long petId){
        Optional<Pet> petOptional = petRepository.findById(petId);
        if (!petOptional.isPresent()){
            return null;
        }
        return petOptional.get();
    }


}