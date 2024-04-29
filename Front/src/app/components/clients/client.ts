import { CompanyStatus } from "./companyStatus";
import { RegistrationType } from "./registrationType";

export interface Client {
    id?: number;
    registrationType: RegistrationType;
    registrationNumber: string;
    clientCode: string;
    name: string;
    fantasyName: string;
    registrationDate: string;
    companyStatus: CompanyStatus;
    accountantId: number;
}