import { Component, OnInit } from '@angular/core';
import { CommonComponent } from '../common.component';
import { Curso } from '../../models/curso';
import { CursoService } from '../../services/curso.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonFormComponent } from '../common-form.component';

@Component({
  selector: 'app-curso-form',
  templateUrl: './curso-form.component.html',
  styleUrls: ['./curso-form.component.css']
})
export class CursoFormComponent extends CommonFormComponent<Curso, CursoService> implements OnInit {

  constructor(service: CursoService,
    router: Router,
    route: ActivatedRoute) {
    super(service, router, route);
    this.titulo = 'Crear Curso';
    this.model = new Curso();
    this.redirect = '/cursos';
    this.nombreModel = Curso.name;
  }

}
