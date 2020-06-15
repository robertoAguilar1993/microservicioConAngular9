import { Component, OnInit } from '@angular/core';
import { CommonComponent } from '../common.component';
import { Examen } from '../../models/examen';
import { ExamenService } from '../../services/examen.service';

@Component({
  selector: 'app-examenes',
  templateUrl: './examenes.component.html',
  styleUrls: ['./examenes.component.css']
})
export class ExamenesComponent extends CommonComponent<Examen, ExamenService> implements OnInit {

  constructor(service: ExamenService) {
    super(service);
     this.titulo = 'Listado de Examenes';
     this.nombreModel = Examen.name;
   }



}
