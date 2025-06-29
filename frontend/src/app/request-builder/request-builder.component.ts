import { Component } from '@angular/core';

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
    // TODO: Implement sending request to backend API
    console.log('Sending request:', this.selectedMethod, this.url);
  }
}
