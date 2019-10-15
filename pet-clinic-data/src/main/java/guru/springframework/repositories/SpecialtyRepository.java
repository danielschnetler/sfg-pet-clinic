package guru.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.sfgpetclinic.model.Specialty;

public interface SpecialtyRepository extends CrudRepository<Specialty, Long>{

}
