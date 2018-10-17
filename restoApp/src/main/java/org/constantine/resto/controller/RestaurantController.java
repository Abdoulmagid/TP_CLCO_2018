package org.constantine.resto.controller;

import org.constantine.resto.exception.ResourceNotFoundException;
import org.constantine.resto.model.*;
import org.constantine.resto.repository.GradeRepository;
import org.constantine.resto.repository.RestaurantRepository;
import org.constantine.resto.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @GetMapping("/restaurants")
    public Page<RestaurantDetail> getAllRestaurants() {
        return restaurantRepository.findAllRestaurantsDetail(PageRequest.of(0, 10, Sort.by("totalRatings").descending().and(Sort.by("ratings").descending())));
    }

    @GetMapping("/restaurants/top5")
    public Page<RestaurantDetail> getTop5Restaurants() {
        return restaurantRepository.findTop5RestaurantsDetail(PageRequest.of(0, 5, Sort.by("totalRatings").descending().and(Sort.by("ratings").descending())));
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant getRestaurant(
            @PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (!restaurant.isPresent())
            throw new ResourceNotFoundException("Restaurant not found with id " + id);
        return restaurant.get();
    }

    @GetMapping("/restaurants/{restoId}/details")
    public ResponseEntity<?> getRestaurantGradeDetail(
            @PathVariable Long restoId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        if (!restaurant.isPresent())
            return new ResponseEntity<>("Restaurant not found with id " + restoId, HttpStatus.NOT_FOUND);

        List<Grade> grades = gradeRepository.findGradesByRestaurant(restaurant.get());
        if (grades.size() == 0)
            return new ResponseEntity<>(
                    new RestaurantDetail(
                            restaurant.get().getId(),
                            restaurant.get().getName(),
                            restaurant.get().getDescription(),
                            restaurant.get().getAddress(),
                            null,
                            null
                            ),
                    HttpStatus.OK);

        return new ResponseEntity<>(
                gradeRepository.findRestaurantDetail(restoId),
                HttpStatus.OK);
    }

    @GetMapping("/restaurants/{restoId}/grades")
    public ResponseEntity<?> getGradesByRestaurantId(
            @PathVariable Long restoId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        return restaurant.<ResponseEntity<?>>
                map(restaurant1 -> new ResponseEntity<>(gradeRepository.findGradesByRestaurant(restaurant1), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Restaurant not found with id " + restoId, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/restaurants/{restoId}/grades/{clientId}")
    public ResponseEntity<?> getGradeByRestaurantIdAndVisitorId(
            @PathVariable Long restoId,
            @PathVariable Long clientId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        if (!restaurant.isPresent())
            return new ResponseEntity<>("Restaurant not found with id " + restoId, HttpStatus.NOT_FOUND);

        Optional<Visitor> client = visitorRepository.findById(clientId);
        if (!client.isPresent())
            return new ResponseEntity<>("Client not found with id " + clientId, HttpStatus.NOT_FOUND);

        Optional<Grade> grade = gradeRepository.findGradeByRestaurantAndVisitor(restaurant.get(), client.get());
        if (!grade.isPresent())
            return new ResponseEntity<>("Grade not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @PostMapping("/restaurants/{restoId}/grades/{clientId}")
    public ResponseEntity<?> graduateRestaurant(
            @PathVariable Long restoId,
            @PathVariable Long clientId,
            @Valid @RequestBody GraduateRequest graduateRequest) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        if (!restaurant.isPresent())
            return new ResponseEntity<>("Restaurant not found with id " + restoId, HttpStatus.NOT_FOUND);

        Optional<Visitor> client = visitorRepository.findById(clientId);
        if (!client.isPresent())
            return new ResponseEntity<>("Client not found with id " + clientId, HttpStatus.NOT_FOUND);

        Optional<Grade> grade = gradeRepository.findGradeByRestaurantAndVisitor(restaurant.get(), client.get());
        if (grade.isPresent())
            return new ResponseEntity<>(grade, HttpStatus.OK);

        Grade newGrade = new Grade(
                restaurant.get(),
                client.get(),
                graduateRequest.getFoodQuality(),
                graduateRequest.getRoomQuality(),
                graduateRequest.getServiceQuality()
        );

        return new ResponseEntity<>(gradeRepository.save(newGrade), HttpStatus.OK);
    }

    @GetMapping("/restaurants/search")
    public Page<RestaurantDetail> searchRestaurant(
            @RequestParam(defaultValue = "", name = "q") String query,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) {
        return restaurantRepository.searchRestaurants("%"+query+"%", PageRequest.of(page, size, Sort.by("totalRatings").descending().and(Sort.by("ratings").descending())));
    }

}
