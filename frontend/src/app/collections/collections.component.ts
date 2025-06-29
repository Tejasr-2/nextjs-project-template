import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Collection {
  id: string;
  name: string;
  description: string;
  requests: any[];
  variables: { [key: string]: any };
  version: string;
}

@Component({
  selector: 'app-collections',
  templateUrl: './collections.component.html',
  styleUrls: ['./collections.component.css']
})
export class CollectionsComponent implements OnInit {
  collections: Collection[] = [];
  selectedCollection: Collection | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadCollections();
  }

  loadCollections() {
    this.http.get<Collection[]>('/api/collections').subscribe(data => {
      this.collections = data;
    });
  }

  selectCollection(collection: Collection) {
    this.selectedCollection = collection;
  }

  addCollection() {
    const newCollection: Collection = {
      id: '',
      name: 'New Collection',
      description: '',
      requests: [],
      variables: {},
      version: '1.0.0'
    };
    this.collections.push(newCollection);
    this.selectedCollection = newCollection;
  }

  saveCollection() {
    if (!this.selectedCollection) return;
    this.http.post<Collection>('/api/collections', this.selectedCollection).subscribe(saved => {
      this.loadCollections();
      this.selectedCollection = saved;
    });
  }

  deleteCollection(collection: Collection) {
    this.http.delete(`/api/collections/${collection.id}`).subscribe(() => {
      this.loadCollections();
      if (this.selectedCollection?.id === collection.id) {
        this.selectedCollection = null;
      }
    });
  }
}
