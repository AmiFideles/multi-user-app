package com.itmo.programming.service;


import com.itmo.programming.controller.command.exceptions.PermissionDeniedModificationException;
import com.itmo.programming.dto.PersonDTO;
import com.itmo.programming.mapper.todto.PersonMapper;
import com.itmo.programming.mapper.toentity.LocationDTOMapper;
import com.itmo.programming.mapper.toentity.PersonDTOMapper;
import com.itmo.programming.model.Location;
import com.itmo.programming.model.Person;
import com.itmo.programming.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    @Transactional
    public long create(PersonDTO personDTO) {
        if (!findByKey(personDTO.getKey()).isPresent()){
            return personRepository.save(PersonDTOMapper.INSTANCE.toEntity(personDTO)).getId();
        }
        return 0;
    }

    @Transactional
    public Optional<PersonDTO> findById(Long id) {
        return personRepository.findById(id).map(PersonMapper.INSTANCE::toDTO);
    }

    @Transactional
    public boolean remove(Long id) {
        Optional<Person> maybePerson = personRepository.findById(id);
        maybePerson.ifPresent(person -> personRepository.delete(person.getId()));
        return maybePerson.isPresent();
    }

    @Transactional
    public List<PersonDTO> findAll() {
        return personRepository.findAll().stream().map(PersonMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }


    @Transactional
    public Optional<PersonDTO> findByKey(Long key) {
        try {
            return personRepository.findByKey(key).map(PersonMapper.INSTANCE::toDTO);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public List<PersonDTO> findByBirthday(LocalDate date) {
        return personRepository.findByBirthday(date).stream().map(PersonMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public long removeByBirthdayAndUserId(LocalDate birthday, long userId) {
        List<Person> beingDeleted = personRepository.findByBirthdayAndUserId(birthday, userId);
        if (!beingDeleted.isEmpty()) {
            long id = beingDeleted.get(0).getId();
            if (remove(id)) return id;
        }
        return 0;
    }

    @Transactional
    public long removeGreater(Person person, long userId) {
        List<PersonDTO> personsDTOByUserId = getPersonsByUserId(userId);
        List<Person> persons = personsDTOByUserId.stream().map(PersonDTOMapper.INSTANCE::toEntity).collect(Collectors.toList());
        persons = persons.stream().filter(element -> element.compareTo(person) > 0).collect(Collectors.toList());
        persons.forEach(element -> remove(element.getId()));
        return persons.size();
    }

    @Transactional
    public long removeGreaterKey(long key, long userId) {
        List<Person> all = personRepository.findByGreaterKeyAndUserId(key, userId);
        if (!all.isEmpty()) {
            all.forEach(person -> personRepository.delete(person.getId()));
        }
        return all.size();
    }

    @Transactional
    public boolean update(long id, Person person, long userId) throws PermissionDeniedModificationException {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isPresent()) {
            Person updatablePerson = byId.get();
            if (updatablePerson.getUser().getId() == userId) {
                updatablePerson.setName(person.getName());
                updatablePerson.setHeight(person.getHeight());
                updatablePerson.setBirthday(person.getBirthday());
                updatablePerson.setPassportID(person.getPassportID());
                updatablePerson.setCoordinates(person.getCoordinates());
                updatablePerson.setLocation(person.getLocation());
                updatablePerson.setColor(person.getColor());
                personRepository.update(updatablePerson);
                return true;
            }
            throw new PermissionDeniedModificationException();
        }
        return false;
    }

    @Transactional
    public long countLessThanLocation(Location location) {
        List<Location> dtoList = findAll().stream().map(PersonDTO::getLocationDTO).map(LocationDTOMapper.INSTANCE::toEntity).collect(Collectors.toList());
        return dtoList.stream().filter(location1 -> location1.compareTo(location) == -1).count();
    }


    @Transactional
    public List<PersonDTO> getPersonsByUserId(long userId) {
        return personRepository.findPersonByUserId(userId).stream().map(PersonMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public long clearByUserId(long userId) {
        List<PersonDTO> personDTOList = getPersonsByUserId(userId);
        personDTOList.forEach(personDTO -> remove(personDTO.getId()));
        return personDTOList.size();
    }

    @Transactional
    public boolean removeByKeyAndUserId(long key, long userId) {
        return personRepository.findByKey(key)
                .filter(person -> person.getUser().getId() == userId)
                .map(person -> {
                    remove(person.getId());
                    return true;
                })
                .orElse(false);
    }
}
