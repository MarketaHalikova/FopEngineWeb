package de.irs.fopengine.fopengineweb.repositories;

import de.irs.fopengine.fopengineweb.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
