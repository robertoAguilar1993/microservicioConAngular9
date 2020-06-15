import { Component, OnInit } from '@angular/core';
import { CommonFormComponent } from '../common-form.component';
import { Examen } from '../../models/examen';
import { ExamenService } from '../../services/examen.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Asignatura } from '../../models/asignatura';
import { Pregunta } from '../../models/pregunta';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-examen-form',
  templateUrl: './examen-form.component.html',
  styleUrls: ['./examen-form.component.css']
})
export class ExamenFormComponent
  extends CommonFormComponent<Examen, ExamenService> implements OnInit {

    asignaturasPadre: Asignatura[]=[];
    asignaturasHija: Asignatura[]=[];

    errorPregunta:string;


  constructor(service: ExamenService,
    router: Router,
    route: ActivatedRoute) {
    super(service, router, route);
    this.titulo = 'Crear Examen';
    this.model = new Examen();
    this.redirect = '/examenes';
    this.nombreModel = Examen.name;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id');
      if (id) {
        this.titulo = 'Editar ' + this.nombreModel;
        this.service.ver(id).subscribe(model => {
          this.model = model;
          this.cargarHijos();
        });
      }
    })

    this.service.findAllAsignatura().subscribe(asignaturas =>{
      this.asignaturasPadre = asignaturas.filter(a=>!a.padre)
    });

  }

  public crear(): void {
    if(this.model.preguntas.length ===0){
      //Swal.fire('Error Pregunta', 'Examen debe tener preguntas', 'error');
      this.errorPregunta = 'Examen debe tener preguntas';
      return;
    }
    this.errorPregunta = undefined;
    this.eliminarPreguntasVacias();
    super.crear();
  }

  public editar(): void {
    if(this.model.preguntas.length ===0){
      //Swal.fire('Error Pregunta', 'Examen debe tener preguntas', 'error');
      this.errorPregunta = 'Examen debe tener preguntas';
      return;
    }
    this.errorPregunta = undefined;
    this.eliminarPreguntasVacias();
    super.editar();
  }

  public cargarHijos(): void{
    this.asignaturasHija = this.model.asignaturaPadre? this.model.asignaturaPadre.hijos:[];
  }

  public compararAsigantura(a1: Asignatura, a2: Asignatura): boolean{
    if(a1 === undefined && a2 === undefined){
      return true;
    }
    if(a1 === null || a2 === null || a1 === undefined || a2 === undefined){
      return false;
    }
    if(a1.id === a2.id){
      return true;
    }
    return false;
  }

  public agregarPregunta():void{
    this.model.preguntas.push(new Pregunta);
    this.errorPregunta = undefined;
  }

  public asignarTexto(pregunta: Pregunta, event:any): void{
    pregunta.texto = event.target.value;
  }

  public eliminarPregunta(pregunta: Pregunta):void{
    this.model.preguntas = this.model.preguntas.filter(p=>p.texto !== pregunta.texto);
  }

  public eliminarPreguntasVacias(): void{
    this.model.preguntas = this.model.preguntas.filter(p=> p.texto !=null && p.texto.length > 0);
  }

}
