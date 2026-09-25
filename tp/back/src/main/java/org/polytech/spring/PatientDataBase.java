package org.polytech.spring;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class PatientDataBase implements PatientStore {
    public void savePatient(Patient p){
        System.out.println("patient");
    }


}
