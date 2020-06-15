import { OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2'
import { Generic } from '../models/generic';
import { CommonService } from '../services/common.service';


export abstract class CommonFormComponent<E extends Generic, S extends CommonService<E>> implements OnInit {

  titulo: string;
  model: E;
  error: any;
  protected redirect: string;
  protected nombreModel: string;

  constructor(protected service: S,
    protected router: Router,
    protected route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id');
      if (id) {
        this.titulo = 'Editar ' + this.nombreModel;
        this.service.ver(id).subscribe(model => {
          this.model = model;
        });
      }
    })

  }

  public crear(): void {
    this.service.crear(this.model).subscribe(model => {
      console.log("Model db: ", model);
      Swal.fire('Nuevo:', `${this.nombreModel} ${model.nombre} creado con éxito`, 'success');
      this.router.navigate([this.redirect])
    }, err => {
      console.log(err)
      if (err.status === 400) {
        this.error = err.error.errors;
      }
    });
  }

  public editar(): void {
    this.service.editar(this.model).subscribe(model => {
      console.log("Model db: ", model);
      Swal.fire('Modificado', `${this.nombreModel} ${model.nombre} actualizado con éxito`, 'success');
      this.router.navigate([this.redirect])
    }, err => {
      console.log(err)
      if (err.status === 400) {
        this.error = err.error.errors;
      }
    });
  }

}
