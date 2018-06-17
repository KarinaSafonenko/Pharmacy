package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.entity.Producer;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.ProducerRepository;
import by.epam.safonenko.pharmacy.specification.impl.producer.find.FindProducers;
import by.epam.safonenko.pharmacy.validator.Validator;

import java.util.List;

public class ProducerLogic implements Logic {
    private ProducerRepository producerRepository;

    public ProducerLogic(){
        producerRepository = new ProducerRepository();
    }

    public List<Producer> findProducers() throws LogicException {
        try {
            return producerRepository.find(new FindProducers());
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public boolean addProducer(String name, String country) throws LogicException {
        if (!Validator.validateInitials(name) || !Validator.validateInitials(country)){
            return false;
        }
        try {
            producerRepository.add(name, country);
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
        return true;
    }
}
