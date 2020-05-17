package backend.repository;

import backend.model.Pin;
import backend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PinRepository extends CrudRepository<Pin, Long>, QueryByExampleExecutor<Pin> {


}



