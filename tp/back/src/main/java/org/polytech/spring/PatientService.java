package org.polytech.spring;

import org.springframework.stereotype.Service;

@Service
public class PatientService {
   private final PatientStore store;

    public PatientService(PatientStore store) {
        this.store = store;
    }

    public void savePatient(Patient patient) {
        if (patient.getEmail() == null) {
            throw new IllegalArgumentException("email obligatoire");
        }
        System.out.println("PatientService   - validation de " + patient.getEmail());
        store.save(patient);
    }
}
