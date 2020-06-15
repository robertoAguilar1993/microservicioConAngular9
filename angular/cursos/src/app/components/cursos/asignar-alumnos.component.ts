import { Component, OnInit,ViewChild } from '@angular/core';
import { Curso } from '../../models/curso';
import { ActivatedRoute } from '@angular/router';
import { CursoService } from '../../services/curso.service';
import { AlumnoService } from '../../services/alumno.service';
import { Alumno } from '../../models/alumno';
import { SelectionModel } from '@angular/cdk/collections';
import Swal from 'sweetalert2';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-asignar-alumnos',
  templateUrl: './asignar-alumnos.component.html',
  styleUrls: ['./asignar-alumnos.component.css']
})
export class AsignarAlumnosComponent implements OnInit {

  curso: Curso;
  alumnosAsignar: Alumno[] = [];
  alumnos: Alumno[] = [];
  tabIndex: number = 0;

  dataSource: MatTableDataSource<Alumno>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  mostrarColumnas: string[] = ['nombre', 'apellidos', 'seleccion'];
  mostrarColumnasAlumnos: string[] = ['id', 'nombre', 'apellidos', 'email', 'seleccion'];

  seleccion: SelectionModel<Alumno> = new SelectionModel<Alumno>(true, []);

  constructor(private route: ActivatedRoute,
    private cursoService: CursoService,
    private alumnoService: AlumnoService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id');
      this.cursoService.ver(id).subscribe(data => {
        this.curso = data;
        this.alumnos = this.curso.alumnos;
        this.initPaginator();
      });
    })
  }

  public initPaginator(): void{
    this.dataSource = new MatTableDataSource<Alumno>(this.alumnos);
    this.dataSource.paginator= this.paginator;
    this.paginator._intl.itemsPerPageLabel = "Registros por pagina"
  }

  public filtrar(nombre: string): void {
    nombre = nombre !== undefined ? nombre.trim() : '';
    if (nombre !== '') {
      this.alumnoService.filtrarPorNombre(nombre).subscribe(alumnos => {
        this.alumnosAsignar = (alumnos as Alumno[]).filter(a => {
          let filtrar = true;
          this.alumnos.forEach(ca => {
            if (a.id === ca.id) {
              filtrar = false;
            }
          });
          return filtrar;
        });
      }, err => {
        if (err.status === 404) {
          this.alumnosAsignar = []
        }
      });
    }
  }

  public estanTodosSeleccionados(): boolean {
    const seleccionados = this.seleccion.selected.length;
    const numAlumnos = this.alumnosAsignar.length;
    return seleccionados === numAlumnos;
  }

  public seleccionarDesSeleccionarTodos(): void {
    this.estanTodosSeleccionados() ?
      this.seleccion.clear() :
      this.alumnosAsignar.forEach(a => this.seleccion.select(a));
  }

  public asignar(): void {
    this.cursoService.asignarAlumnos(this.curso, this.seleccion.selected)
      .subscribe(data => {
        this.tabIndex = 2;
        Swal.fire(
          'Asigandos:',
          `Alumnos Asigandos con éxito al curso ${this.curso.nombre}`,
          'success')
        console.log(data);
        this.alumnos = this.alumnos.concat(this.seleccion.selected);
        this.initPaginator();
        this.alumnosAsignar = [];
        this.seleccion.clear();
      }, err => {
        if (err.status === 500) {
          const mensaje: string = err.error.message;
          if (mensaje.indexOf("ConstraintViolationException") > -1) {
            Swal.fire(
              'Cuidado',
              `No se puede asignar el alumno ya está asociado a otro curso.`,
              'error');
          }
        }
      });
  }

  public eliminarAlumno(alumno: Alumno): void {

    Swal.fire({
      title: 'Cuidado:',
      text: `¿Seguro que desea eliminar a ${alumno.nombre}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!!!'
    }).then((result) => {
      if (result.value) {
        this.cursoService.eliminarAlumno(this.curso, alumno).subscribe(curso => {
          this.alumnos = this.alumnos.filter(a => a.id !== alumno.id);
          this.initPaginator();
          Swal.fire(
            'Eliminado:',
            `Alumno ${alumno.nombre} eliminaado con éxito del curso ${curso.nombre}.`,
            'success'
          )
        });
      }
    });

  }

}
