export interface Patient {
  id: number;
  firstName: string;
  lastName: string;
  securityNumber: string;
  patientDetails: PatientDetails;
};

export interface PatientDetails {
  age: number;
  height: number;
  weight: number;
  bloodGroup: BloodGroup;
  dioptry: Dioptry;
  vaccinations: Array<Vaccination>;
  surgeries: Array<Surgery>;
};

interface BloodGroup {
  type: BloodGroupType;
  rhPositive: boolean;
};

enum BloodGroupType {
  A, B, AB, O
};

interface Dioptry {
  left: number;
  right: number;
};

interface Vaccination {
  name: string;
  date: Date;
};

interface Surgery {
  name: string;
  date: Date;
};
