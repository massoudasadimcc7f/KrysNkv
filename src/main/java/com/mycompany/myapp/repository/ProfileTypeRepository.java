package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.ProfileType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProfileType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileTypeRepository extends JpaRepository<ProfileType, Long> {

}
