package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.ProfileVariant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProfileVariant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileVariantRepository extends JpaRepository<ProfileVariant, Long> {

}
