package com.plumstep.repository;

import com.plumstep.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: joutwate
 * Date: 9/16/16
 * Time: 11:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
    PersonalInfo findBySsn(String ssn);
}
