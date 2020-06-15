import { Injectable } from '@angular/core';
import { CommonService } from './common.service';
import { Examen } from '../models/examen';
import { BASE_ENDPOINT } from '../config/app';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Asignatura } from '../models/asignatura';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ExamenService extends CommonService<Examen>{

  protected baseEndpoint = BASE_ENDPOINT + '/examenes'

  constructor(http: HttpClient) { 
    super(http);
  }

  public findAllAsignatura():Observable<Asignatura[]>{
    return this.http.get(`${this.baseEndpoint}/asignaturas`).pipe(
      map((res: any)=>{
        return res.result;
      })
    );
  }

  public filtrarPorNombre(nombre: string): Observable<Examen[]>{
    return this.http.get(`${this.baseEndpoint}/filtrar/${nombre}`).pipe(
      map((data: any)=>{
        return data.result;
      })
    );
  }

}
