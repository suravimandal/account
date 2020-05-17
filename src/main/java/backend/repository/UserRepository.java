package backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import backend.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, QueryByExampleExecutor<User> {
	

}
