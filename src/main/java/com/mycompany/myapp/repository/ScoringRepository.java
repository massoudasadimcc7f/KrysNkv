package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.Scoring;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Scoring entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScoringRepository extends JpaRepository<Scoring, Long> {

}
