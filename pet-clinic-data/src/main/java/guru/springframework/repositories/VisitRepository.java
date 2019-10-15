package guru.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.sfgpetclinic.model.Visit;

public interface VisitRepository extends CrudRepository<Visit, Long>{

}
