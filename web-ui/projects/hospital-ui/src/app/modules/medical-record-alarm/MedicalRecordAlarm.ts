export interface MedicalRecordAlarm{
  id: string,
  eventId: number,
  time: Date,
  severity: string,
  eventSource: string,
  eventCode: number,
  message: string,
  alarmCode: number
}
