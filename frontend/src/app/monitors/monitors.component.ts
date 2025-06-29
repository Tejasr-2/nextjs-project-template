import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-monitors',
  templateUrl: './monitors.component.html',
  styleUrls: ['./monitors.component.css']
})
export class MonitorsComponent implements OnInit {
  monitors: any[] = [];
  selectedMonitor: any = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadMonitors();
  }

  loadMonitors() {
    this.http.get<any[]>('/api/monitors').subscribe(data => {
      this.monitors = data;
    });
  }

  selectMonitor(monitor: any) {
    this.selectedMonitor = monitor;
  }

  addMonitor() {
    const newMonitor = {
      id: '',
      name: 'New Monitor',
      collectionId: '',
      schedule: '',
      emailAlert: '',
      slackWebhookUrl: '',
      lastRun: null,
      lastStatus: ''
    };
    this.monitors.push(newMonitor);
    this.selectedMonitor = newMonitor;
  }

  saveMonitor() {
    if (!this.selectedMonitor) return;
    this.http.post<any>('/api/monitors', this.selectedMonitor).subscribe(saved => {
      this.loadMonitors();
      this.selectedMonitor = saved;
    });
  }

  deleteMonitor(monitor: any) {
    this.http.delete(`/api/monitors/${monitor.id}`).subscribe(() => {
      this.loadMonitors();
      if (this.selectedMonitor?.id === monitor.id) {
        this.selectedMonitor = null;
      }
    });
  }
}
