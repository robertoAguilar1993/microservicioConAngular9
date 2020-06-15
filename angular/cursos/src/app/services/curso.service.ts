import { Injectable } from '@angular/core';
import { CommonService } from './common.service';
import { Curso } from '../models/curso';
import { HttpClient } from '@angular/common/http';
import { BASE_ENDPOINT } from '../config/app';
import { Alumno } from '../models/alumno';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Examen } from '../models/examen';

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso>{
  protected baseEndpoint = BASE_ENDPOINT + '/cursos';
  constructor(http: HttpClient) { 
    super(http);
  }

  public asignarAlumnos(curso:Curso, alumnos:Alumno[]): Observable<Curso>{
    return this.http.put(`${this.baseEndpoint}/${curso.id}/asignar-alumnos`,
    alumnos,
    {headers: this.cabeceras}).pipe(
      map((data: any)=>{
        return data.result;
      })
    );
  }

  public eliminarAlumno(curso:Curso, alumno:Alumno): Observable<Curso>{
    return this.http.put(`${this.baseEndpoint}/${curso.id}/eliminar-alumno`,
    alumno,
    {headers: this.cabeceras}).pipe(
      map((data: any)=>{
        return data.result;
      })
    );
  }

  public asignarExamenes(curso: Curso, examenes: Examen[]): Observable<Curso>{
    return this.http.put(`${this.baseEndpoint}/${curso.id}/asignar-examenes`,
      examenes,
      {headers: this.cabeceras}).pipe(
        map((data:any)=>{
          return data.result;
        })
      );
  }
  public eliminarExamen(curso: Curso, examen: Examen): Observable<Curso>{
    return this.http.put(`${this.baseEndpoint}/${curso.id}/eliminar-examen`,
      examen,
      {headers: this.cabeceras}).pipe(
        map((data:any)=>{
          return data.result;
        })
      );
  }

  public obtenerCursoPorAlumnoId(alumno: Alumno):Observable<Curso>{
    return this.http.get(`${this.baseEndpoint}/alumno/${alumno.id}`).pipe(
      map((data:any)=>{
        return data.result;
      })
    );
  }
}
