import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Sidebar } from './shared/components/sidebar/sidebar';
import { AuthService } from './services/auth';
import { ToastComponent } from './shared/components/toast/toast.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Sidebar, CommonModule, ToastComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  sidebarCollapsed = false;

  constructor(public authService: AuthService) {}

  onSidebarToggle(collapsed: boolean) {
    this.sidebarCollapsed = collapsed;
  }
}
