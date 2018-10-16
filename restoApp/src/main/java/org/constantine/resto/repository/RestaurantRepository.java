package org.constantine.resto.repository;

import org.constantine.resto.model.Restaurant;
import org.constantine.resto.model.RestaurantDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT " +
            "new org.constantine.resto.model.RestaurantDetail(" +
            "g.restaurant.id as id, " +
            "g.restaurant.name as name, " +
            "g.restaurant.description as description, " +
            "g.restaurant.address as address, " +
            "AVG(g.quality) as ratings, " +
            "COUNT(g.quality) as totalRatings) " +
            "FROM Grade g " +
            "GROUP BY g.restaurant.id " +
            "HAVING g.restaurant.name LIKE :q")
    Page<RestaurantDetail> searchRestaurants(@Param("q") String query, Pageable pageable);
    @Query("SELECT " +
            "new org.constantine.resto.model.RestaurantDetail(" +
            "g.restaurant.id as id, " +
            "g.restaurant.name as name, " +
            "g.restaurant.description as description, " +
            "g.restaurant.address as address, " +
            "AVG(g.quality) as ratings, " +
            "COUNT(g.quality) as totalRatings) " +
            "FROM Grade g " +
            "GROUP BY g.restaurant.id")
    Page<RestaurantDetail> findAllRestaurantsDetail(Pageable pageable);
    @Query("SELECT " +
            "new org.constantine.resto.model.RestaurantDetail(" +
            "g.restaurant.id as id, " +
            "g.restaurant.name as name, " +
            "g.restaurant.description as description, " +
            "g.restaurant.address as address, " +
            "AVG(g.quality) as ratings, " +
            "COUNT(g.quality) as totalRatings) " +
            "FROM Grade g " +
            "GROUP BY g.restaurant.id")
    Page<RestaurantDetail> findTop5RestaurantsDetail(Pageable pageable);

}
