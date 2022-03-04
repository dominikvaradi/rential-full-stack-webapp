package hu.dominikvaradi.rentialwebapp.repository;

import hu.dominikvaradi.rentialwebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
