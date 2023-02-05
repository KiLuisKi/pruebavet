package org.springframework.samples.petclinic.vet;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpecialtyRepository extends Repository<Specialty, Integer> {

	void save(Specialty specialty);

	Specialty findById(@Param("id") Integer id);

}
