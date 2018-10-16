package org.constantine.resto.repository;

import org.constantine.resto.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, GradeId> {

    List<Grade> findGradesByRestaurant(Restaurant restaurant);
    @Query("SELECT " +
            "new org.constantine.resto.model.RestaurantDetail(" +
            "restaurant.id as id, " +
            "restaurant.name as name, " +
            "restaurant.description as description, " +
            "restaurant.address as address, " +
            "AVG(quality) as ratings, " +
            "COUNT(quality) as totalRatings) " +
            "FROM Grade WHERE id.restaurantId = :id")
    RestaurantDetail findRestaurantDetail(@Param("id") Long restoId);
    Optional<Grade> findGradeByRestaurantAndVisitor(Restaurant restaurant, Visitor visitor);
}
