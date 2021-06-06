export interface MedicalRecordLog {
  id: number, 
  age: number,
  patientId: number,
  time: Date,
  heartBeat: number,
  averageHeartBeat: number,
  bloodPressure: String,
  averageBloodPressure: String, 
  bodyTemperature: number,
  vaccinated: boolean,
  surgery: String

}