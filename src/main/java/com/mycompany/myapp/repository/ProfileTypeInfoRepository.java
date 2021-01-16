package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.ProfileTypeInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProfileTypeInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileTypeInfoRepository extends JpaRepository<ProfileTypeInfo, Long> {

}
