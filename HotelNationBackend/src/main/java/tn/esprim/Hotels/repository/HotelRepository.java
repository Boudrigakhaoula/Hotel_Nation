package tn.esprim.Hotels.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprim.Hotels.model.Hotel;
import java.util.List;

public interface HotelRepository extends MongoRepository<Hotel, String> {
    List<Hotel> findByLocation(String location);
    List<Hotel> findByStars(String stars);
    List<Hotel> findByPriceLessThanEqual(Double price);
    List<Hotel> findByLocationAndStars(String location, String stars);
} 