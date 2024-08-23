package com.springboot.jpa.springboot_jpa;

import com.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.springboot.jpa.springboot_jpa.entities.Person;
import com.springboot.jpa.springboot_jpa.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.SortedMap;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;


	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		whereIn();


	}

	@Transactional(readOnly = true)
	public void subQueries(){
		System.out.println("Consulta por el nombre mas corto y su largo");
		List<Object[]> registers = repository.getShorterName();
		registers.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name=" + name +", length" + length);
		});

	}


	@Transactional(readOnly = true)
	public void whereIn(){
		System.out.println("Consulta Where in");
		List<Person> persons = repository.getPersonByIds();
		persons.forEach(System.out::println);
	}


	@Transactional(readOnly = true)
	public void queriesFunctionAggregation(){
		System.out.println("Consulta con total de registros de la tabla");
		Long count = repository.totalPerson();
		System.out.println(count);

		System.out.println("Consulta con el valor minimo del id");
		Long min = repository.minId();
		System.out.println(min);

		System.out.println("Consulta con el valor maximo del id");
		Long max = repository.maxId();
		System.out.println(max);

		System.out.println("Consulta con el nombre y su largo");
		List<Object[]> regs = repository.getPersonNameLength();
		regs.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name=" + name +", length" + length);
		});

		System.out.println("Consulta con el nombre mas corto");
		Integer minName = repository.getMinLengthName();
		System.out.println(minName);

		System.out.println("Consulta con el nombre mas largo");
		Integer maxName = repository.getMaxLengthName();
		System.out.println(maxName);

		System.out.println("Consultas de resumen de funciones de agregacion");
		Object[] resumeReg = (Object[]) repository.getResumeAggregation();
		System.out.println("min=" + resumeReg[0] + ", max=" + resumeReg[1] + ", sum="+resumeReg[2] + "avg=" + resumeReg[3] + ", count=" +resumeReg[4]);

	}


	@Transactional
	public void update(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		optionalPerson.ifPresent(person -> {
			System.out.println(person);
			System.out.println("Ingrese el lenguage de programacion: ");
			String programmingLanguage = scanner.next();
			person.setProgramminglanguage(programmingLanguage);
			repository.save(person);
		});

		scanner.close();
	}


	@Transactional(readOnly = true)
	public void personalizedQueries2(){

		List<Object[]> personRegs = repository.findAllMixPerson();

		personRegs.forEach(reg -> {
			System.out.println("language=" + reg[1] + ", person="+reg[0]);
		});

		System.out.println("Devuelve una instancia personalizada");
		List<Person> persons = repository.findAllPersonalizedObjectPerson();
		persons.forEach(System.out::println);

		System.out.println("Consulta devuelve objeto DTO personalizada");
		List<PersonDto> personDtos = repository.findAllPersonDto();
		personDtos.forEach(System.out::println);

	}


	@Transactional(readOnly = true)
	public void personalizedQueriesDistinctConcatUpperAndLowerCase(){

		System.out.println("Consulta nombres y apellidos de personas");
		List<String> names = repository.findAllFullNameConcat();
		names.forEach(System.out::println);

		System.out.println("Consulta nombres y apellidos de personas con otro metodo");
		names = repository.findAllFullNameConcatWithOr();
		names.forEach(System.out::println);

		System.out.println("Consulta nombres y apellidos con mayusculas");
		names = repository.findAllFullNameConcatUpper();
		names.forEach(System.out::println);

		System.out.println("Consulta nombres y apellidos con minusculas");
		names = repository.findAllFullNameConcatLower();
		names.forEach(System.out::println);

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesBetween(){

		System.out.println("Consulta por rangos");
		List<Person> persons = repository.findAllBetweenId(2L,5L);
		persons.forEach(System.out::println);

		System.out.println("Consulta por nombre entre letras del alfabeto");
		persons = repository.findAllBetweenName();
		persons.forEach(System.out::println);

		System.out.println("Consulta por nombre de las personas por orden alfabetico ascendentemente");
		persons = repository.findAllBetweenNameOrdered();
		persons.forEach(System.out::println);

		System.out.println("Consulta por nombre de las personas por orden alfabetico descendentemente");
		persons = repository.findAllBetweenNameOrderedDesc();
		persons.forEach(System.out::println);

		System.out.println("Consulta por id de las personas por orden numero descendiente");
		persons = repository.findByIdBetweenOrderByIdDesc(2L,4L);
		persons.forEach(System.out::println);

	}


	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct(){
		System.out.println("Consultas con nombres de personas");
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);

		System.out.println("Consultas con nombres unicos de personas");
		names = repository.findAllNamesDistinct();
		names.forEach(System.out::println);

		System.out.println("Consultas con lenguages unicos de personas");
		names = repository.findAllLanguagesDistinct();
		names.forEach(System.out::println);

		System.out.println("Consulta el total de lenguages de programacion unicas");
		Long totalLanguages = repository.findAllLanguagesDistinctCount();
		System.out.println(totalLanguages);

	}


	@Transactional(readOnly = true)
	public void personalizedQueries(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Escriba el id:");
		Long id = scanner.nextLong();

		String name = repository.getNameById(id);
		System.out.println(name);

		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

		System.out.println("Consulta por campos personalizados por id");
		Object[] personReg = (Object[]) repository.obtenerPersonDataFullById(id);
		System.out.println("id= "+ personReg[0] + " nombre="+personReg[1]+ " apellido="+personReg[2] + " lenguage="+personReg[3] );


		scanner.close();
	}

	@Transactional
	public void delete(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar");
		Long id = scanner.nextLong();
		repository.deleteById(id);

		repository.findAll().forEach(System.out::println);
		scanner.close();
	}

	@Transactional
	public void delete2(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		optionalPerson.ifPresentOrElse(
				//person -> repository.delete(person),
				repository::delete,
				() -> System.out.println("El id escrito no existe"));

		repository.findAll().forEach(System.out::println);
		scanner.close();
	}


	@Transactional
	public void create(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("Registre los datos del nuevo miembro");
		String name = scanner.next();
		String lastname = scanner.next();
		String programmingLanguage = scanner.next();
		scanner.close();


		Person person = new Person(null, name,lastname,programmingLanguage);

		Person personNew = repository.save(person);
		System.out.println(personNew);

		repository.findById(personNew.getId()).ifPresent(System.out::println);

	}

	public void findOne(Long id){
//		Person person = repository.findById(id).orElseThrow();
//		System.out.println(person);

//		repository.findById(id).ifPresent(System.out::println);

		Person person = null;
		Optional<Person> optionalPerson = repository.findById(id);
		if(optionalPerson.isPresent()){
			person = optionalPerson.get();
		}


//		Person person = repository.findOneLikeName("le").orElseThrow();
//		Person person = repository.findByNameContaining("le").orElseThrow();
		System.out.println(person);
	}

	public void list(){

		//List<Person> persons = (List<Person>) repository.findAll();
		//List<Person> persons = (List<Person>) repository.findByProgrammingLanguage("C++");
		//List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("C++");
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("C++","Jose");
		persons.stream().forEach(System.out::println);


		List<Object[]> personsValues = repository.obtenerPersonData();
		personsValues.stream().forEach(p -> {
			System.out.println(p[0] + " es experto en " + p[1]);
		});


		List<Object[]> personsNameLanguage = repository.obtenerPersonData("Alex","Javascript");
		personsNameLanguage.stream().forEach(p -> {
			System.out.println(p[0] + " no sabe nada de " + p[1]);
		});



	}
}
