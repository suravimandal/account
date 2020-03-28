package backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import backend.model.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> , QueryByExampleExecutor<Token> {

}
