export interface Certificate {
  serialNumber: number,
  name: string,
  surname: string,
  countryCode: string,
  email: string,
  organization: string,
  organizationUnitName: string,
  startDate: Date,
  endDate: Date
}

export interface IssueCertificateData {
  csrId: string,
  issuerAlias: string;
  subjectData: SubjectData,
  startDate: Date,
  endDate: Date,
  extensions: Extensions
}

export interface SubjectData {
  subjectId: string,
  commonName: string,
  givenName: string,
  surname: string,
  countryCode: string,
  email: string,
  organization: string,
  organizationUnitName: string
};

export interface Extensions {
  keyUsage: KeyUsage;
  basicConstraints: BasicConstraints;
  subjectKeyId: boolean;
  authorityKeyId: boolean;
}

export interface KeyUsage {
  crlSign: boolean;
  dataEncipherment: boolean;
  decipherOnly: boolean;
  keyAgreement: boolean;
  keyCertSign: boolean;
  keyEncipherment: boolean;
  nonRepudiation: boolean;
  digitalSignature: boolean;
  encipherOnly: boolean;

  keyUsageIsCritical: boolean;
}
export interface BasicConstraints {
  ca: boolean;
  pathLen: number;
  basicConstraintsIsCritical: boolean;
}

export interface Template {
  id: number;
  name: string;
  extensions: Extensions;
};
