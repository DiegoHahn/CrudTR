import { Client } from "./client";

export interface ClientResponse {
    content: Client[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    numberOfElements: number;
}