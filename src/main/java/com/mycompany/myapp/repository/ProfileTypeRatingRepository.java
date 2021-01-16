package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.ProfileTypeRating;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProfileTypeRating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileTypeRatingRepository extends JpaRepository<ProfileTypeRating, Long> {

}
