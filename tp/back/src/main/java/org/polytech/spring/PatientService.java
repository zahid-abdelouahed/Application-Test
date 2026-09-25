package org.polytech.spring;

import org.springframework.stereotype.Service;

@Service
public class PatientService {
    private final PatientStore store;
    public PatientService(PatientStore patientStore) {
        this.store = patientStore;
    }
}
