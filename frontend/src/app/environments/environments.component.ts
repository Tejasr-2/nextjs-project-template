import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Environment {
  id: string;
  name: string;
  variables: { [key: string]: string };
}

@Component({
  selector: 'app-environments',
  templateUrl: './environments.component.html',
  styleUrls: ['./environments.component.css']
})
export class EnvironmentsComponent implements OnInit {
  environments: Environment[] = [];
  selectedEnvironment: Environment | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadEnvironments();
  }

  loadEnvironments() {
    this.http.get<Environment[]>('/api/environments').subscribe(data => {
      this.environments = data;
    });
  }

  selectEnvironment(env: Environment) {
    this.selectedEnvironment = env;
  }

  addEnvironment() {
    const newEnv: Environment = { id: '', name: 'New Environment', variables: {} };
    this.environments.push(newEnv);
    this.selectedEnvironment = newEnv;
  }

  saveEnvironment() {
    if (!this.selectedEnvironment) return;
    this.http.post<Environment>('/api/environments', this.selectedEnvironment).subscribe(saved => {
      this.loadEnvironments();
      this.selectedEnvironment = saved;
    });
  }

  deleteEnvironment(env: Environment) {
    this.http.delete(`/api/environments/${env.id}`).subscribe(() => {
      this.loadEnvironments();
      if (this.selectedEnvironment?.id === env.id) {
        this.selectedEnvironment = null;
      }
    });
  }
}
