import { Component, OnInit } from '@angular/core';
import { CommonService } from '../../services/common.service';
import { CommonComponent } from '../common.component';
import { Generic } from '../../models/generic';
import { Curso } from '../../models/curso';
import { CursoService } from '../../services/curso.service';

@Component({
  selector: 'app-cursos',
  templateUrl: './cursos.component.html',
  styleUrls: ['./cursos.component.css']
})
export class CursosComponent extends CommonComponent<Curso, CursoService> implements OnInit {


  constructor(service: CursoService) {
    super(service);
    this.titulo = 'Listado de Cursos';
    this.nombreModel = Curso.name;
   }
}
