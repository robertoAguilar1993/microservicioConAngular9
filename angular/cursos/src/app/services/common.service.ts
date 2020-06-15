import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Generic } from '../models/generic';


export  abstract class CommonService<E extends Generic> {

  /*this.result = result;
  this.errors = errors;
  this.operationStatus = false;
  private Integer totalPages;
  private Long totalElementos;
  */

  protected baseEndpoint: string;
  
  protected cabeceras: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })
  

  constructor(protected http: HttpClient) { }

  public listar(): Observable<E[]>{
    return this.http.get(this.baseEndpoint)
    .pipe(
      map((res: any) => {
        return res.result;
      }));
  }

  public listarPorPaginas(page:string, size: string): Observable<any>{
    const params = new HttpParams()
    .set('page', page)
    .set('size', size);
    return this.http.get<any>(`${this.baseEndpoint}/pagina`, {params: params});
  }


  public ver(id: number):Observable<E>{
    return this.http.get(`${this.baseEndpoint}/${id}`).pipe(
      map((res:any)=>{
        return res.result
      })
    );
  }

  public crear(e: E):Observable<E>{
    return this.http.post(this.baseEndpoint, e, {headers: this.cabeceras}).pipe(
      map((resp:any)=>{
        return resp.result;
      })
    );
  }

  public editar(e: E):Observable<E>{
    return this.http.put(`${this.baseEndpoint}/${e.id}`, e, {headers: this.cabeceras}).pipe(
      map((resp:any)=>{
        return resp.result;
      })
    );
  }

  public eliminar(id: number):Observable<void>{
    return this.http.delete<void>(`${this.baseEndpoint}/${id}`);
  }

}
