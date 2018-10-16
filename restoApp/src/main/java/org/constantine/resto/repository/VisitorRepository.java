package org.constantine.resto.repository;

import org.constantine.resto.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long>  {
    Boolean existsByUsername(String username);
    Visitor findByUsernameAndPassword(String username, String password);
}
