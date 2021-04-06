export interface Certificate {
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
  csrId: number,
  commonName: string,
  givenName: string,
  surname: string,
  countryCode: string,
  email: string,
  organization: string,
  organizationUnitName: string
  startDate: Date,
  endDate: Date
}