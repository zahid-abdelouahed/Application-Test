package org.polytech.spring;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class PatientDataBase implements PatientStore {
    @Override
    public void save(Patient p){
        System.out.println("patient");
    }


}
