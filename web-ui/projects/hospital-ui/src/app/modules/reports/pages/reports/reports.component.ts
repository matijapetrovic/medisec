import { Component, OnInit } from '@angular/core';
import { ReportService } from '../../report.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {

  data: any;
  basicOptions: any;

  basicData: any =  {
    labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    datasets: [
        {
            label: 'First Dataset',
            data: [65, 59, 80, 81, 56, 55, 40],
            fill: false,
            borderColor: '#42A5F5'
        },
        // {
        //     label: 'Second Dataset',
        //     data: [28, 48, 40, 19, 86, 27, 90],
        //     fill: false,
        //     borderColor: '#FFA726'
        // }
    ]
  }

  constructor(private service: ReportService) {
    this.data = {
      datasets: [{
          data: [
          ],
          backgroundColor: [
              "#FF6384",
              "#4BC0C0",
              "#FFCE56",
              "#E7E9ED",
              "#36A2EB"
          ],
          label: 'My dataset'
      }],
      labels: [
      ]
     }
   }

  ngOnInit(): void {
    this.applyLightTheme();
    this.service.getReport().subscribe((res) => {
      this.data.labels = res.polarArea.logTypes;
      this.data.datasets[0].data = res.polarArea.countEachType;
    });
    this.service.getChartData().subscribe(basicData => this.basicData = basicData);
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
