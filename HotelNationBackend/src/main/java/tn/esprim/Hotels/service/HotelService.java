package tn.esprim.Hotels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprim.Hotels.model.Hotel;
import tn.esprim.Hotels.repository.HotelRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {
    
    @Autowired
    private HotelRepository hotelRepository;
    
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
    
    public Optional<Hotel> getHotelById(String id) {
        return hotelRepository.findById(id);
    }
    
    public List<Hotel> getHotelsByLocation(String location) {
        return hotelRepository.findByLocation(location);
    }
    
    public List<Hotel> getHotelsByStars(String stars) {
        return hotelRepository.findByStars(stars);
    }

    public List<Hotel> getHotelsByMaxPrice(String maxPrice) {
           try {

                           // Get all hotels and filter them
                          return hotelRepository.findAll().stream()
                        .filter(hotel -> {
                            try {
                                       // Clean the hotel price string and convert to double
                                                String hotelPrice = hotel.getPrice().replace("TND", "").trim();
                                       double priceValue = Double.parseDouble(hotelPrice);
                                       double maxPriceValue = Double.parseDouble(maxPrice);

                                              return priceValue <= maxPriceValue;
                                } catch (NumberFormatException e) {
                                     return false;
                                   }
                          })
                       .collect(Collectors.toList());
           } catch (Exception e) {
                    return Collections.emptyList();
               }
        }
    
    public List<Hotel> getHotelsByLocationAndStars(String location, String stars) {
        return hotelRepository.findByLocationAndStars(location, stars);
    }
    
   /* public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
    
  /*  public void deleteHotel(String id) {
        hotelRepository.deleteById(id);
    }*/
} 