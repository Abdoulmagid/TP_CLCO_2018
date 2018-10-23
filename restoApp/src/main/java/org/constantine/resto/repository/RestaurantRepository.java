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
            "r.id as id, " +
            "r.name as name, " +
            "r.description as description, " +
            "r.address as address, " +
            "AVG(g.quality) as ratings, " +
            "COUNT(g.quality) as totalRatings) " +
            "FROM Restaurant r " +
            "LEFT JOIN Grade g ON r.id = g.id.restaurantId " +
            "GROUP BY r.id " +
            "HAVING r.name LIKE :q ")
    Page<RestaurantDetail> searchRestaurants(@Param("q") String query, Pageable pageable);

    @Query("SELECT " +
            "new org.constantine.resto.model.RestaurantDetail(" +
            "r.id as id, " +
            "r.name as name, " +
            "r.description as description, " +
            "r.address as address, " +
            "AVG(g.quality) as ratings, " +
            "COUNT(g.quality) as totalRatings) " +
            "FROM Restaurant r " +
            "LEFT JOIN Grade g ON r.id = g.id.restaurantId " +
            "GROUP BY r.id ")
    Page<RestaurantDetail> findAllRestaurantsDetail(Pageable pageable);

}
