/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.Visit;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 *
 */

@Slf4j
@SpringBootApplication
@ImportRuntimeHints(PetClinicRuntimeHints.class)
public class PetClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoVetRepository(VetRepository vetRepository, SpecialtyRepository specialtyRepository,
			PetRepository petRepository) {
		return (args) -> {
			Logger log = Logger.getLogger(PetClinicApplication.class.getName());

			log.info("*****************************************************");
			log.info("BOOTCAMP - Spring y Spring Data - vetRepository");
			log.info("*****************************************************");

			log.info("Creamos un objeto Vet");
			Vet vet = new Vet();
			vet.setFirstName("Sergio");
			vet.setLastName("Raposo Vargas");
			log.info("Tienes id " + vet.getId());

			log.info("Persistimos en BBDD");
			vetRepository.save(vet);

			log.info("Comprobamos que se ha creado correctamente");
			Vet vetAux = vetRepository.findById(vet.getId());
			log.info(vetAux.toString());
			log.info("Editamos el objeto y añadimos una Speciality");

			Specialty s = specialtyRepository.findById(1);
			vet.addSpecialty(s);
			vetRepository.save(vet);
			log.info(vet.toString());

			log.info("Listamos todos los veterinarios");
			for (Vet v : vetRepository.findAll()) {
				log.info("Vet: " + v.getId() + "  " + v.getFirstName() + v.getSpecialties());
			}

			log.info("Listamos todos los veterinarios que tengan el mismo apellido");
			for (Vet v : vetRepository.findByLastName("apellidoprueba")) {
				log.info("Vet: " + v.getId() + "  " + v.getFirstName() + "  " + v.getLastName());
			}

			log.info("Listamos todos los veterinarios que tengan el mismo nombre y apellido");
			for (Vet v : vetRepository.findByFirstNameAndLastName("Sergio", "Raposo Vargas")) {
				log.info("Vet: " + v.getId() + "  " + v.getFirstName() + "  " + v.getLastName());
			}

			log.info("Mascotas nacidas en 2010 ordenadas por fecha de nacimiento");
			LocalDate fecha = LocalDate.now();
			for (Pet p : petRepository.findByBirthDateOrderByBirthDateAsc(fecha)) {
				log.info("Pet: " + p.getId() + "  " + p.getName() + "  " + p.getBirthDate());
			}

			log.info("Creamos la visita");
			Visit visit = new Visit();
			visit.setDescription("Visita de prueba");
			log.info("Añadimos la visita a las pets");
			for (Pet p : petRepository.findAll()) {
				p.addVisit(visit);
				petRepository.save(p);
				log.info("Pet: " + p.getId() + "  " + p.getName() + "  " + p.getVisits());
			}
		};
	}

}
