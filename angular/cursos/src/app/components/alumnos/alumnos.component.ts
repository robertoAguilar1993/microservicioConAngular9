import { Component, OnInit, ViewChild } from '@angular/core';
import { Alumno } from '../../models/alumno';
import { AlumnoService } from '../../services/alumno.service';
import { CommonComponent } from '../common.component';
import { BASE_ENDPOINT } from '../../config/app';


@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html',
  styleUrls: ['./alumnos.component.css']
})
export class AlumnosComponent extends CommonComponent <Alumno, AlumnoService> implements OnInit {

  public baseEndpoint = BASE_ENDPOINT + '/alumnos';

  constructor(service: AlumnoService) {
    super(service);
    this.titulo = 'Listado de Alumnos';
    this.nombreModel = Alumno.name;
   }

}
