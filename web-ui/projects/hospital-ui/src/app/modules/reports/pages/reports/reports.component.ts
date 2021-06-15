import { Component, OnInit } from '@angular/core';
import { ChartAlarmData, ServiceLogReport } from '../../report';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {

  serviceLogReport: ServiceLogReport;
  chartAlarmData: ChartAlarmData;

  data: any = {
    datasets: [{
        data: [
        ],
        backgroundColor: [
            "#FF6384",
            "#4BC0C0",
            "#FFCE56",
            "#E7E9ED",
            "#36A2EB",
            "#422F2B",
            "#952E18",
            "#43CEA3"

        ],
        label: 'My dataset'
    }],
    labels: [
    ]
   };
  basicOptions: any;

  basicData: any =  {
    labels: [],
    datasets: [
        {
            label: 'First Dataset',
            data: [],
            fill: false,
            borderColor: '#42A5F5'
        }
    ]
  }

  constructor(private service: ReportService) {
   }

  ngOnInit(): void {
    this.applyLightTheme();
    this.service.getReport().subscribe((res) => {
      this.serviceLogReport = res;
      console.log(res.polarArea.logTypes);
      this.data.datasets[0].data = res.polarArea.countEachType;
      this.data.labels = res.polarArea.logTypes;
    });
    this.service.getChartData().subscribe(basicData => {
      this.chartAlarmData = basicData;
      this.basicData.labels = basicData.labels;
      this.basicData.datasets[0].data = basicData.data;
    });
  }

  applyLightTheme(){
    this.basicOptions = {
      legend: {
          labels: {
              fontColor: '#495057'
          }
      },
      scales: {
          xAxes: [{
              ticks: {
                  fontColor: '#495057'
              }
          }],
          yAxes: [{
              ticks: {
                  fontColor: '#495057'
              }
          }]
      }
  };
  }

}
