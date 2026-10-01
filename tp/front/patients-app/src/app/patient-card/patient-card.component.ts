import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Patient } from '../patient.model';

@Component({
  selector: 'app-patient-card',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './patient-card.component.html',
  styleUrl: './patient-card.component.css'
})
export class PatientCardComponent {

   patient = signal<Patient>({
    id: 2,
    prenom : 'Abdel',
    nom : 'Zahid',
    datedenaissance:'25-10-2003',
    email: 'abdo@gmail.com'
   })
    nom = signal<string>('');
    effaceNom(){
      this.nom.set("")
    }
}
