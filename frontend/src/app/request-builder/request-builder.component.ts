import { Component } from '@angular/core';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-request-builder',
  templateUrl: './request-builder.component.html',
  styleUrls: ['./request-builder.component.css']
})
export class RequestBuilderComponent {
  httpMethods = ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'HEAD', 'OPTIONS'];
  selectedMethod = 'GET';
  url = '';
  headers: { key: string; value: string }[] = [];
  params: { key: string; value: string }[] = [];
  body = '';
  authType = 'None';
  authData: any = {};

  constructor(private apiService: ApiService) {}

  addHeader() {
    this.headers.push({ key: '', value: '' });
  }

  removeHeader(index: number) {
    this.headers.splice(index, 1);
  }

  addParam() {
    this.params.push({ key: '', value: '' });
  }

  removeParam(index: number) {
    this.params.splice(index, 1);
  }

  sendRequest() {
    const requestPayload = {
      method: this.selectedMethod,
      url: this.url,
      headers: this.headers.reduce((acc, cur) => {
        if (cur.key) acc[cur.key] = cur.value;
        return acc;
      }, {} as {[key: string]: string}),
      params: this.params.reduce((acc, cur) => {
        if (cur.key) acc[cur.key] = cur.value;
        return acc;
      }, {} as {[key: string]: string}),
      body: this.body,
      auth: this.authType !== 'None' ? this.authData : null
    };

    this.apiService.sendRequest(requestPayload).subscribe({
      next: (response) => {
        console.log('Response:', response);
        // TODO: Pass response to ResponseViewerComponent
      },
      error: (error) => {
        console.error('Error:', error);
      }
    });
  }
}
