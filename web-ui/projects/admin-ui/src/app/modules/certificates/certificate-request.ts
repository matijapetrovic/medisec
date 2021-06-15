export interface CertificateRequest {
  id: string,

  commonName: string,
  surname: string,
  givenName: string,
  organization: string,
  organizationUnit: string,
  countryCode: string,
  email: String
};
