package tn.esprim.Hotels.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprim.Hotels.model.Hotel;
import tn.esprim.Hotels.service.HotelService;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String id) {
        return hotelService.getHotelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Hotel>> getHotelsByLocation(@PathVariable String location) {
        List<Hotel> hotels = hotelService.getHotelsByLocation(location);
        if (hotels.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/stars/{stars}")
    public List<Hotel> getHotelsByStars(@PathVariable String stars) {
        return hotelService.getHotelsByStars(stars);
    }

    @GetMapping("/price/{maxPrice}")
    public ResponseEntity<List<Hotel>> getHotelsByMaxPrice(@PathVariable String maxPrice) {
        List<Hotel> hotels = hotelService.getHotelsByMaxPrice(maxPrice);
        if (hotels.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/search")
    public List<Hotel> getHotelsByLocationAndStars(
            @RequestParam String location,
            @RequestParam String stars) {
        return hotelService.getHotelsByLocationAndStars(location, stars);
    }

    /*
    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.saveHotel(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(
            @PathVariable String id,
            @RequestBody Hotel hotel) {
        return hotelService.getHotelById(id)
                .map(existingHotel -> {
                    hotel.setId(id);
                    return ResponseEntity.ok(hotelService.saveHotel(hotel));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok().build();
    }*/
} 