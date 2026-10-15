import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Api } from '../../services/api';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  totalUsuarios = 0;
  totalLibros = 0;
  totalPrestamos = 0;

  constructor(private api: Api) {}

  ngOnInit() {
    this.api.getUsuarios(0, 1).subscribe((res) => {
      this.totalUsuarios = res.totalElements;
    });
    this.api.getLibros(0, 1).subscribe((res) => {
      this.totalLibros = res.totalElements;
    });
    this.api.getPrestamos(0, 1).subscribe((res) => {
      this.totalPrestamos = res.totalElements;
    });
  }
}
