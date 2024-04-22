import { Accountant } from "./accountant";

export interface AccountantResponse {
    content: Accountant[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    numberOfElements: number;
}