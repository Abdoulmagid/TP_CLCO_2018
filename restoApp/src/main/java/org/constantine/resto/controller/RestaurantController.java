package org.constantine.resto.controller;

import org.constantine.resto.error.CustomError;
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

    @GetMapping("/restaurants/{restoId}/details")
    public ResponseEntity<?> getRestaurantGradeDetail(
            @PathVariable Long restoId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        if (!restaurant.isPresent())
            return new ResponseEntity<>( new CustomError("Restaurant not found with id " + restoId), HttpStatus.OK);

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
                .orElseGet(() -> new ResponseEntity<>(new CustomError("Restaurant not found with id " + restoId), HttpStatus.OK));
    }

    @GetMapping("/restaurants/{restoId}/grades/{clientId}")
    public ResponseEntity<?> getGradeByRestaurantIdAndVisitorId(
            @PathVariable Long restoId,
            @PathVariable Long clientId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        if (!restaurant.isPresent())
            return new ResponseEntity<>(new CustomError("Restaurant not found with id " + restoId), HttpStatus.OK);

        Optional<Visitor> client = visitorRepository.findById(clientId);
        if (!client.isPresent())
            return new ResponseEntity<>(new CustomError("Client not found with id " + clientId), HttpStatus.OK);

        Optional<Grade> grade = gradeRepository.findGradeByRestaurantAndVisitor(restaurant.get(), client.get());
        if (!grade.isPresent())
            return new ResponseEntity<>(new CustomError("Grade not found"), HttpStatus.OK);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @PostMapping("/restaurants/{restoId}/grades/{clientId}")
    public ResponseEntity<?> graduateRestaurant(
            @PathVariable Long restoId,
            @PathVariable Long clientId,
            @Valid @RequestBody GraduateRequest graduateRequest) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        if (!restaurant.isPresent())
            return new ResponseEntity<>(new CustomError("Restaurant not found with id " + restoId), HttpStatus.OK);

        Optional<Visitor> client = visitorRepository.findById(clientId);
        if (!client.isPresent())
            return new ResponseEntity<>(new CustomError("Client not found with id " + clientId), HttpStatus.OK);

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
