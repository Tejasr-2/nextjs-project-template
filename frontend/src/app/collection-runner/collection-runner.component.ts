import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-collection-runner',
  templateUrl: './collection-runner.component.html',
  styleUrls: ['./collection-runner.component.css']
})
export class CollectionRunnerComponent implements OnInit {
  collections: any[] = [];
  selectedCollection: any = null;
  dataFile: File | null = null;
  runResults: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadCollections();
  }

  loadCollections() {
    this.http.get<any[]>('/api/collections').subscribe(data => {
      this.collections = data;
    });
  }

  onFileSelected(event: any) {
    this.dataFile = event.target.files[0];
  }

  runCollection() {
    if (!this.selectedCollection || !this.dataFile) {
      alert('Please select a collection and a data file.');
      return;
    }

    const reader = new FileReader();
    reader.onload = (e: any) => {
      let dataSets;
      try {
        dataSets = JSON.parse(e.target.result);
      } catch {
        alert('Invalid JSON data file.');
        return;
      }

      const payload = {
        collection: this.selectedCollection,
        dataSets: dataSets
      };

      this.http.post<any[]>('/api/collection-runner/run', payload).subscribe(results => {
        this.runResults = results;
      });
    };
    reader.readAsText(this.dataFile);
  }
}
