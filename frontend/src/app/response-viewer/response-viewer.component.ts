import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-response-viewer',
  templateUrl: './response-viewer.component.html',
  styleUrls: ['./response-viewer.component.css']
})
export class ResponseViewerComponent {
  @Input() statusCode: number | null = null;
  @Input() time: number | null = null;
  @Input() size: number | null = null;
  @Input() body: string | null = null;
  @Input() cookies: any[] = [];

  viewMode: 'pretty' | 'raw' | 'preview' = 'pretty';

  setViewMode(mode: 'pretty' | 'raw' | 'preview') {
    this.viewMode = mode;
  }

  get prettyBody() {
    try {
      return JSON.stringify(JSON.parse(this.body || ''), null, 2);
    } catch {
      return this.body;
    }
  }
}
