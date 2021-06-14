import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DialogService } from 'primeng/dynamicdialog';
import { LogSource } from '../../log-source';
import { LogsService } from '../../logs.service';
import { LogSourceFormComponent } from '../log-source-form/log-source-form.component';

@Component({
  selector: 'app-log-sources-list',
  templateUrl: './log-sources-list.component.html',
  styleUrls: ['./log-sources-list.component.scss'],
  providers: [DialogService]
})
export class LogSourcesListComponent implements OnInit {
  logSources: LogSource[];

  constructor(private logService: LogsService, private dialogService: DialogService) {
  }

  ngOnInit(): void {
    this.getLogSources();
  }

  getLogSources(): void {
    this.logService.getLogSources().subscribe((logSources) => this.logSources = logSources);
  }

  addLogSource(): void {
    this.logSources.push({path: '', readFrequency: 0, filter: ''});
  }

  updateLogSource(source: LogSource) {
    this.dialogService.open(LogSourceFormComponent, {
      data: {
        source: source
      }
    });
  }

  deleteLogSource(source: LogSource) {
    const idx = this.logSources.findIndex((logSource) => logSource == source);
    this.logSources.splice(idx, 1);
  }

  saveLogSources() {
    for (const source of this.logSources) {
      if (source.path.length == 0) {
        return;
      }
    }
    this.logService.updateLogSources(this.logSources).subscribe(() => this.getLogSources());
  }

}
