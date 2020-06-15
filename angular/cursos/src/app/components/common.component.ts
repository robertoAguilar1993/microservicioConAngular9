import {  OnInit, ViewChild } from '@angular/core';
import Swal from 'sweetalert2'
import { PageEvent, MatPaginator } from '@angular/material/paginator';
import { Generic } from '../models/generic';
import { CommonService } from '../services/common.service';


export abstract class CommonComponent<E extends Generic, S extends CommonService<E>> implements OnInit {

  titulo:string;
  nombreModel:string;
  lista : E[];

  totalRegistros: number = 0;
  totalPorPagina: number = 5;
  paginaActual: number = 0;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private service: S) { }

  ngOnInit(): void {
    this.listar();
  }

  public paginar(event: PageEvent): void{
    this.paginaActual =  event.pageIndex;
    this.totalPorPagina = event.pageSize;
    this.listar();
  }

  public listar(){
    const paginaActual = this.paginaActual + '';
    const totalPorPagina= this.totalPorPagina + "";
    this.service.listarPorPaginas(paginaActual, totalPorPagina)
      .subscribe((data =>{
      this.lista = data.result;
      this.totalRegistros = data.totalElementos;
      this.paginator._intl.itemsPerPageLabel = 'Registros por página: '
    }));
  }

  public eliminar(model: E): void{

    Swal.fire({
      title: 'Cuidado:',
      text: `¿Seguro que desea eliminar a ${model.nombre}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!!!'
    }).then((result) => {
      if (result.value) {
        this.service.eliminar(model.id).subscribe(()=>{
          this.listar();
          Swal.fire('Eliminado', `${this.nombreModel} ${model.nombre} eliminado con exito`, 'success');
        })
      }
    })
  }

}
