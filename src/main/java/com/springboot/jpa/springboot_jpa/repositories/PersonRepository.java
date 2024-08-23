package com.springboot.jpa.springboot_jpa.repositories;

import com.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.springboot.jpa.springboot_jpa.entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person,Long> {

    @Query("SELECT p FROM Person p WHERE p.id in (2,4,5)")
    List<Person> getPersonByIds();

    @Query("SELECT p.name, LENGTH(p.name) FROM Person p WHERE length(p.name)=(SELECT MIN(LENGTH(p.name)) FROM Person p)")
    List<Object[]> getShorterName();

    @Query("SELECT MIN(p.id), MAX(p.id), SUM(p.id), AVG(LENGTH(p.name)), COUNT(p.id) FROM Person p")
    public Object getResumeAggregation();

    @Query("SELECT MIN(LENGTH(p.name)) FROM Person p")
    public Integer getMinLengthName();

    @Query("SELECT MAX(LENGTH(p.name)) FROM Person p")
    public Integer getMaxLengthName();

    @Query("SELECT p.name, LENGTH(p.name) FROM Person p")
    List<Object[]> getPersonNameLength();

    @Query("SELECT COUNT(p) FROM Person p ")
    Long totalPerson();

    @Query("SELECT MIN(p.id) FROM Person p")
    Long minId();

    @Query("SELECT MAX(p.id) FROM Person p")
    Long maxId();

    List<Person> findByIdBetween(Long id1, Long id2);

    @Query("SELECT p FROM Person p WHERE p.id between ?1 and ?2")
    List<Person> findAllBetweenId(Long one, Long two);

    @Query("SELECT p FROM Person p WHERE p.name between 'A' and 'x' ")
    List<Person> findAllBetweenName();

    @Query("SELECT p FROM Person p WHERE p.name between 'A' and 'x' order by p.name ")
    List<Person> findAllBetweenNameOrdered();

    @Query("SELECT p FROM Person p WHERE p.name between 'A' and 'x' order by p.name DESC ")
    List<Person> findAllBetweenNameOrderedDesc();

    List<Person> findByIdBetweenOrderByIdDesc(Long id1, Long id2);

    @Query("SELECT CONCAT(p.name, ' ', p.lastname) FROM Person p")
    List<String> findAllFullNameConcat();

    @Query("SELECT p.name || ' ' || p.lastname FROM Person p")
    List<String> findAllFullNameConcatWithOr();

    @Query("SELECT UPPER(CONCAT(p.name, ' ', p.lastname)) FROM Person p")
    List<String> findAllFullNameConcatUpper();

    @Query("SELECT LOWER(p.name || ' ' || p.lastname) FROM Person p")
    List<String> findAllFullNameConcatLower();

    @Query("SELECT p.name FROM Person p")
    List<String> findAllNames();

    @Query("SELECT DISTINCT(p.name) FROM Person p")
    List<String> findAllNamesDistinct();

    @Query("SELECT DISTINCT(p.programmingLanguage) FROM Person p")
    List<String> findAllLanguagesDistinct();

    @Query("SELECT COUNT(DISTINCT(p.programmingLanguage)) FROM Person p")
    Long findAllLanguagesDistinctCount();

    @Query("SELECT new com.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) FROM Person p")
    List<PersonDto> findAllPersonDto();

    @Query("SELECT new Person(p.name, p.lastname) FROM Person p")
    List<Person> findAllPersonalizedObjectPerson();

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage=?1")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);

    List<Person>findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p")
    List<Object[]> obtenerPersonData();

    @Query("SELECT p, p.programmingLanguage FROM Person p")
    List<Object[]> findAllMixPerson();

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage FROM Person p")
    List<Object[]> obtenerPersonDataFull();

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage FROM Person p WHERE p.id =?1")
    Object obtenerPersonDataFullById(Long id);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.name=?1 AND p.programmingLanguage=?2")
    List<Object[]> obtenerPersonData(String name, String programmingLanguage);

    @Query("SELECT p FROM Person p Where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("SELECT p FROM Person p Where p.name=?1")
    Optional<Person> findOneName(String name);

    @Query("SELECT p FROM Person p Where p.name LIKE %?1%")
    Optional<Person> findOneLikeName(String name);

    Optional<Person> findByNameContaining(String name);

    @Query("SELECT p.name FROM Person p WHERE p.id=?1")
    String getNameById(Long id);

    @Query("SELECT CONCAT(p.name, ' ', p.lastname) AS fullname FROM Person p WHERE p.id=?1")
    String getFullNameById(Long id);


}
