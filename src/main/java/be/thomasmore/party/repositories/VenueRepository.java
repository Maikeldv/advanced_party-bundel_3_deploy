package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VenueRepository extends CrudRepository<Venue, Integer> {
    Iterable<Venue> findByOutdoor(boolean isOutdoor);
    Iterable<Venue> findByIndoor(boolean isIndoor);



    Optional<Venue> findFirstByIdLessThanOrderByIdDesc(Integer id);
    Optional<Venue> findFirstByOrderByIdDesc();
    Optional<Venue> findFirstByIdGreaterThanOrderByIdAsc(Integer id);
    Optional<Venue> findFirstByOrderByIdAsc();


    @Query("select v from Venue v where v.capacity>= :min")
    List<Venue> findByCapacityGreaterThanEqual(@Param("min") int min);
    @Query("select v from Venue v where v.capacity>= :min and v.capacity<= :max")
    List<Venue> findByCapacityBetween(int min, int max);

    List<Venue> findAllBy();
}
