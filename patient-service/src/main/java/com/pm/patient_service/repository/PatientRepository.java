package com.pm.patient_service.repository;

import com.pm.patient_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    boolean existsByEmail(String email);
    /*
        Check if there is a patient with this email
        in the repository, with a different ID
        So if the patient has the email, and has the
        id provided it will return false

     */
    boolean existsByEmailAndIdNot(String email, UUID id);
}
