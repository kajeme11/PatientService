package com.pm.patient_service.controller;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.dto.validators.CreatePatientValidationGroup;
import com.pm.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")     //http://localhost:4000/patients
@Tag(name = "Patient", description = "Api for managing patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get all available patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients(){
        List<PatientResponseDTO> patients = patientService.getPatients();

        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a patient")
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    /*
       http://localhost:4000/patients/43
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a patient by ID")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
                                                            @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a patient by ID.")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        // 204 = no content
        return ResponseEntity.noContent().build();
    }
}
