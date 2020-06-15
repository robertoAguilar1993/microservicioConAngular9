import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Alumno } from '../models/alumno';
import { CommonService } from './common.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BASE_ENDPOINT } from '../config/app';

@Injectable({
  providedIn: 'root'
})
export class AlumnoService extends CommonService<Alumno>{

  protected baseEndpoint = BASE_ENDPOINT + '/alumnos';

  constructor(http: HttpClient) {
    super(http)
  }

  public crearConFoto(alumno: Alumno, archivo: File): Observable<Alumno> {
    const formData = new FormData();
    formData.append('archivo', archivo);
    formData.append('nombre', alumno.nombre);
    formData.append('apellidos', alumno.apellidos);
    formData.append('email', alumno.email);
    return this.http.post(this.baseEndpoint + '/crear-con-foto',
      formData).pipe(
        map((res: any) => {
          return res.result;
        })
      );
  }

  public editarConFoto(alumno: Alumno, archivo: File): Observable<Alumno> {
    const formData = new FormData();
    formData.append('archivo', archivo);
    formData.append('nombre', alumno.nombre);
    formData.append('apellidos', alumno.apellidos);
    formData.append('email', alumno.email);
    return this.http.put<Alumno>(`${this.baseEndpoint}/editar-con-foto/${alumno.id}`,
      formData).pipe(
        map((res: any) => {
          return res.result;
        })
      );
  }

  public filtrarPorNombre(nombre:string): Observable<Alumno[]>{
    return this.http.get(`${this.baseEndpoint}/filtrar/${nombre}`).pipe(
      map((res: any)=>{
        return res.result;
      })
    );
  }

}
