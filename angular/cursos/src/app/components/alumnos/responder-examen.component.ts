import { Component, OnInit, ViewChild } from '@angular/core';
import { Alumno } from '../../models/alumno';
import { Curso } from '../../models/curso';
import { Examen } from '../../models/examen';
import { ActivatedRoute } from '@angular/router';
import { CursoService } from '../../services/curso.service';
import { AlumnoService } from '../../services/alumno.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { ResponderExamenModalComponent } from './responder-examen-modal.component';
import { RespuestaService } from '../../services/respuesta.service';
import { Respuesta } from '../../models/respuesta';
import Swal from 'sweetalert2';
import { VerExamenModalComponent } from './ver-examen-modal.component';

@Component({
  selector: 'app-responder-examen',
  templateUrl: './responder-examen.component.html',
  styleUrls: ['./responder-examen.component.css']
})
export class ResponderExamenComponent implements OnInit {

  titulo: string = 'Listado de examenes';
  alumno: Alumno;
  curso: Curso;
  examenes: Examen[] = [];
  mostrarColumnasExamenes: string[] = ['id', 'nombre', 'asignatura', 'preguntas', 'responder', 'ver'];

  dataSource: MatTableDataSource<Examen>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(private route: ActivatedRoute,
    private alumnoService: AlumnoService,
    private cursoService: CursoService,
    private respuestaService: RespuestaService,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params=>{
      const id:number = +params.get('id');
      if(id){
        this.alumnoService.ver(id).subscribe(a=>{
          this.alumno = a;
          this.cursoService.obtenerCursoPorAlumnoId(this.alumno).subscribe(c=>{
            this.curso = c; 
            this.examenes =(c && c.examenes)? c.examenes:[];
            this.initPaginator();
          });
        });
      }
    })
  }

  private initPaginator(): void {
    this.dataSource = new MatTableDataSource<Examen>(this.examenes);
    this.dataSource.paginator = this.paginator;
    this.paginator._intl.itemsPerPageLabel = "Registros por pagina"
  }
  public responderExamen(examen: Examen): void{
    const modalRef = this.dialog.open(ResponderExamenModalComponent, {
      width: '750px',
      data: {
        curso: this.curso,
        alumno: this.alumno,
        examen: examen
      }
    });

    
    modalRef.afterClosed().subscribe((respuestasMap: Map<number, Respuesta>) =>{
      console.log('modal responder examen ha sido enviado y cerrado', respuestasMap);
      if(respuestasMap){
        const respuestas:Respuesta[] = Array.from(respuestasMap.values());
        this.respuestaService.crear(respuestas).subscribe(rs=>{
          examen.respondido = true;
          Swal.fire('Enviadas',
          'Preguntas enviadas con éxito',
          'success' );
        });
      }
    });

  }

  public verExamen(examen:Examen): void{

    this.respuestaService.obtenerRespuestasPorAlumnoPorExamenId(this.alumno, examen).subscribe(rs=>{
      const modalRef = this.dialog.open(VerExamenModalComponent, {
        width: '750px',
        data: {
          curso: this.curso,
          examen: examen,
          respuestas: rs
        }
      });

      modalRef.afterClosed().subscribe(()=>{
        console.log("cerrando modal de ver")
      });
    });
  }

}
