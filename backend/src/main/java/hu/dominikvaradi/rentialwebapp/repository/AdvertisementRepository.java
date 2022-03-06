package hu.dominikvaradi.rentialwebapp.repository;

import hu.dominikvaradi.rentialwebapp.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAllByAdvertiserUserUsername(String username);
}
