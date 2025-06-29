import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-search-analytics',
  templateUrl: './search-analytics.component.html',
  styleUrls: ['./search-analytics.component.css']
})
export class SearchAnalyticsComponent {
  query: string = '';
  results: any[] = [];

  constructor(private http: HttpClient) {}

  search() {
    if (!this.query.trim()) {
      this.results = [];
      return;
    }
    this.http.get<any[]>(`/api/search?q=${encodeURIComponent(this.query)}`).subscribe({
      next: (res) => this.results = res,
      error: () => alert('Error performing search')
    });
  }
}
