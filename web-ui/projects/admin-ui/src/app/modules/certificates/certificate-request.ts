export interface CertificateRequest {
  id: number,

  commonName: string,
  surname: string,
  givenName: string,
  organization: string,
  organizationUnit: string,
  countryCode: string,
  email: String
};
