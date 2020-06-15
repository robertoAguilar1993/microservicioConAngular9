import { Component, OnInit, ViewChild } from '@angular/core';
import { Curso } from '../../models/curso';
import { ActivatedRoute, Router } from '@angular/router';
import { ExamenService } from '../../services/examen.service';
import { CursoService } from '../../services/curso.service';
import { FormControl } from '@angular/forms';
import { Examen } from '../../models/examen';
import { map, flatMap } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import Swal from 'sweetalert2';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-asignar-examenes',
  templateUrl: './asignar-examenes.component.html',
  styleUrls: ['./asignar-examenes.component.css']
})
export class AsignarExamenesComponent implements OnInit {

  curso: Curso;
  autocompleteControl = new FormControl();
  examenesFiltrados: Examen[] = [];
  examenesAsiganar: Examen[] = [];

  examenes: Examen[] = [];
  tabIndex: number = 0;
  dataSource: MatTableDataSource<Examen>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  mostrarColumnas: string[] = ['nombre', 'asignatura', 'eliminar'];
  mostrarColumnasExamenes: string[] = ['id', 'nombre', 'asignatura', 'eliminar'];


  constructor(private route: ActivatedRoute,
    private router: Router,
    private cursoService: CursoService,
    private examenService: ExamenService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idCurso: number = +params.get('id');
      this.cursoService.ver(idCurso).subscribe(curso => {
        this.curso = curso;
        this.examenes = curso.examenes;
        this.initPaginator();
      })
    });
    this.autocompleteControl.valueChanges.pipe(
      map(value => typeof value === "string" ? value : value.nombre),
      flatMap(value => value ? this.examenService.filtrarPorNombre(value) : [])
    ).subscribe(exameness => this.examenesFiltrados = exameness);

  }

  public initPaginator(): void {
    this.dataSource = new MatTableDataSource<Examen>(this.examenes);
    this.dataSource.paginator = this.paginator;
    this.paginator._intl.itemsPerPageLabel = "Registros por pagina"
  }

  public mostrarNombre(examen?: Examen): string {
    return examen ? examen.nombre : '';
  }

  public seleccionarExamen(event: MatAutocompleteSelectedEvent): void {
    const examen = event.option.value as Examen;
    if (!this.existe(examen.id)) {
      this.examenesAsiganar = this.examenesAsiganar.concat(examen);
      this.autocompleteControl.setValue('');
      event.option.deselect();
      event.option.focus();
    } else {
      Swal.fire('Error',
        `El examen "${examen.nombre}" ya está asignado al curso`,
        'error');
        this.autocompleteControl.setValue('');
        event.option.deselect();
        event.option.focus();
    }

  }

  private existe(id: number): boolean {
    let existe = false;
    this.examenesAsiganar.concat(this.examenes).forEach(e => {
      if (id === e.id) {
        existe = true;
      }
    });
    return existe;
  }

  public eliminarDelAsignar(examen: Examen): void {
    this.examenesAsiganar = this.examenesAsiganar.filter(e => examen.id !== e.id);

  }

  public asignar(): void {
    this.cursoService.asignarExamenes(this.curso, this.examenesAsiganar)
      .subscribe(curso => {
        this.examenes = this.examenes.concat(this.examenesAsiganar);
        this.examenesAsiganar = [];
        this.initPaginator();
        Swal.fire(
          'Asigandos:',
          `Examenes Asigandos con éxito al curso ${curso.nombre}`,
          'success');
        this.tabIndex = 2;
      })
  }

  public eliminarExamenDelCurso(examen: Examen): void {
    Swal.fire({
      title: 'Cuidado:',
      text: `¿Seguro que desea eliminar a ${examen.nombre}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!!!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.value) {
        this.cursoService.eliminarExamen(this.curso, examen).subscribe(curso => {
          this.examenes = this.examenes.filter(e => e.id !== examen.id);
          this.initPaginator();
          Swal.fire(
            'Eliminado:',
            `Examen ${examen.nombre} eliminaado con éxito del curso ${curso.nombre}.`,
            'success'
          )
        });
      }
    });
  }

}
