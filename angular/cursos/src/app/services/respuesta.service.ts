import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { BASE_ENDPOINT } from '../config/app';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Alumno } from '../models/alumno';
import { Examen } from '../models/examen';

@Injectable({
  providedIn: 'root'
})
export class RespuestaService {

  private baseEndpoint: string = BASE_ENDPOINT + '/respuestas';
  
  private cabeceras: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })
  

  constructor(private http: HttpClient) { }


  public crear(respuestas: Respuesta[]): Observable<Respuesta[]>{
    return this.http.post(`${this.baseEndpoint}`, respuestas,{
      headers: this.cabeceras
    }).pipe(
      map((data: any)=>{
        return data.result;
      })
    );
  }

  public obtenerRespuestasPorAlumnoPorExamenId(alumno: Alumno, examen:Examen): Observable<Respuesta[]>{
    return this.http.get(`${this.baseEndpoint}/alumno/${alumno.id}/examen/${examen.id}`).pipe(
      map((data:any)=>{
        return data.result;
      })
    );
  }


}
