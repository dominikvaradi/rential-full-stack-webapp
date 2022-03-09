package hu.dominikvaradi.rentialwebapp.repository;

import hu.dominikvaradi.rentialwebapp.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAllByUser_Id(Long id);
    Optional<Advertisement> deleteAdvertisementById(Long id);
}
