import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { TestBed, fakeAsync, tick } from "@angular/core/testing";
import { Accountant } from "../accountants/accountant";
import { AccountantResponse } from "../accountants/accountant-response";
import { Client } from './client';
import { ClientResponse } from "./client.response";
import { ClientService } from "./clients.service";
import { CompanyStatus } from './companyStatus';
import { RegistrationType } from './registrationType';


describe('ClientService', () => {
    let service: ClientService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [ClientService]
    });

    service = TestBed.inject(ClientService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('listClientsData', () => {
    it('should fetch accountants data', fakeAsync(() => {
      const mockResponse: ClientResponse = {
        content: [],
        totalElements: 0,
        totalPages: 0,
        size: 0,
        number: 0,
        numberOfElements: 0
      };

      service.listClientsData('test', 1, 10).subscribe(response => {
        expect(response).toEqual(mockResponse);
      });
      tick();

      const req = httpMock.expectOne('http://localhost:8080/clients?name=test&page=1&size=10');
      expect(req.request.method).toBe('GET');
      req.flush(mockResponse);
    }));

    it('should return an error', fakeAsync(() => {
        service.listClientsData('test', 1, 10).subscribe(() => {}, error => {
        expect(error).toBeInstanceOf(Error);
      });
      tick();

      const req = httpMock.expectOne('http://localhost:8080/clients?name=test&page=1&size=10');
      expect(req.request.method).toBe('GET');
      req.flush(null, { status: 500, statusText: 'Internal Server Error' });
    }));
  });

  describe('listAccountantsData', () => {
    it('should fetch accountants data', fakeAsync(() => {
        const mockResponse: AccountantResponse = {
            content: [],
            totalElements: 0,
            totalPages: 0,
            size: 0,
            number: 0,
            numberOfElements: 0
        };

        service.listAccountantsData('test', 1, 10).subscribe(response => {
        expect(response).toEqual(mockResponse);
        });
        tick();

        const req = httpMock.expectOne('http://localhost:8080/accountants?name=test&page=1&size=10');
        expect(req.request.method).toBe('GET');
        req.flush(mockResponse);
    }));
  });

  describe('getAccountants', () => {
    it('should fetch accountants', fakeAsync(() => {
        const mockAccountants: Accountant [] = [{
            id: 1,
            name: 'Test',
            registrationNumber: '123456',
            accountantCode: '123456',
            isActive: true
        }];
        
        service.getAccountants().subscribe((response: Accountant[]) =>{
            expect(response).toEqual(mockAccountants)
        })
        
        const req = httpMock.expectOne('http://localhost:8080/accountants/all');
            expect(req.request.method).toBe('GET');
            req.flush(mockAccountants);
    }));
  });
    
  describe('create', () => {
    it('should create a client', () => {
        const mockClient: Client = {
            registrationType:RegistrationType.CPF, registrationNumber:"34388376094", 
            clientCode:"123",
            name:"Cliente1 ", 
            fantasyName:"Cliente1 Ltda", 
            registrationDate:"2024-04-18T17:56:46.970Z", 
            companyStatus:CompanyStatus.ACTIVE, 
            accountantId:52, accountant: { name: "teste1", registrationNumber: "59023624076", accountantCode: "123", isActive: false }
        }

    service.create(mockClient).subscribe((response:  Client) => {
      expect(response).toEqual(mockClient);
    });

    const req = httpMock.expectOne('http://localhost:8080/clients');
    expect(req.request.method).toBe('POST');
    req.flush(mockClient);
    });
  });

  describe('edit', () => {
    it('should edit an client', () => {
        const mockClient: Client = {
            id: 1,
            registrationType:RegistrationType.CPF, registrationNumber:"34388376094", 
            clientCode:"123",
            name:"Cliente1 ", 
            fantasyName:"Cliente1 Ltda", 
            registrationDate:"2024-04-18T17:56:46.970Z", 
            companyStatus:CompanyStatus.ACTIVE, 
            accountantId:52, accountant: { name: "teste1", registrationNumber: "59023624076", accountantCode: "123", isActive: false }
        }

        service.edit(mockClient).subscribe((response:Client) => {
        expect(response).toEqual(mockClient);
        });

        const req = httpMock.expectOne('http://localhost:8080/clients/1');
        expect(req.request.method).toBe('PUT');
        req.flush(mockClient);
    });
  });

  describe('delete', () => {
    it('should delete an accountant', () => {
        const clientID = 1;

        service.delete(clientID).subscribe((response:Client) => {
        expect(response).toEqual({});
        });

        const req = httpMock.expectOne('http://localhost:8080/clients/1');
        expect(req.request.method).toBe('DELETE');
    });
  });

  describe('searchClientByID', () => {
    it('should search an client by ID', () => {
        const clientID = 1;
        const mockClient: Client = {
            id: 1,
            registrationType:RegistrationType.CPF, registrationNumber:"34388376094", 
            clientCode:"123",
            name:"Cliente1 ", 
            fantasyName:"Cliente1 Ltda", 
            registrationDate:"2024-04-18T17:56:46.970Z", 
            companyStatus:CompanyStatus.ACTIVE, 
            accountantId:52, accountant: { name: "teste1", registrationNumber: "59023624076", accountantCode: "123", isActive: false }
        }

        service.searchClientByID(clientID).subscribe((response:Client) => {
        expect(response).toEqual(mockClient);
        });

        const req = httpMock.expectOne('http://localhost:8080/clients/1');
        expect(req.request.method).toBe('GET');
        req.flush(mockClient);
    });
  });

  describe('searchAccountantByID', () => {
    it('should search an accountant by ID', () => {
        const accountantID = 1;
        const mockAccountant: Accountant = {
        id: 1,
        name: 'Test',
        registrationNumber: '123456',
        accountantCode: '123456',
        isActive: true
        };

        service.searchAccountantByID(accountantID).subscribe((response:Accountant) => {
        expect(response).toEqual(mockAccountant);
        });

        const req = httpMock.expectOne('http://localhost:8080/accountants/1');
        expect(req.request.method).toBe('GET');
        req.flush(mockAccountant);
    });
  });

  
});