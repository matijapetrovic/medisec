export interface ServiceLogReport{
  totalLogs: number,
  totalLogsLastMonth: number,
  polarArea: PolarAreaData
}

export interface PolarAreaData{
  countEachType: number[],
  logTypes: string[]
}

export interface ChartAlarmData{
  labels: number[],
  data: number[]
}
