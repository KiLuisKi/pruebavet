package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface PetRepository extends Repository<Pet, Integer> {

	Collection<Pet> findByBirthDateOrderByBirthDateAsc(@Param("birth_date") LocalDate date);

}
