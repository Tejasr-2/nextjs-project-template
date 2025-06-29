import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-graphql',
  templateUrl: './graphql.component.html',
  styleUrls: ['./graphql.component.css']
})
export class GraphqlComponent {
  query: string = '';
  variables: string = '{}';
  response: any = null;

  constructor(private http: HttpClient) {}

  executeQuery() {
    let vars;
    try {
      vars = JSON.parse(this.variables);
    } catch {
      alert('Invalid JSON in variables');
      return;
    }

    this.http.post('/api/graphql', { query: this.query, variables: vars }).subscribe({
      next: (res) => this.response = res,
      error: (err) => alert('Error executing GraphQL query')
    });
  }
}
