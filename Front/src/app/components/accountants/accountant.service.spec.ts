import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { TestBed, fakeAsync, tick } from "@angular/core/testing";
import { Accountant } from "./accountant";
import { AccountantResponse } from "./accountant-response";
import { AccountantService } from "./accountants.service";

describe('AccountantService', () => {
  let service: AccountantService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AccountantService]
    });

    service = TestBed.inject(AccountantService);
    httpMock = TestBed.inject(HttpTestingController);
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

    it('should return an error', fakeAsync(() => {
      service.listAccountantsData('test', 1, 10).subscribe(() => {}, error => {
        expect(error).toBeInstanceOf(Error);
      });
      tick();

      const req = httpMock.expectOne('http://localhost:8080/accountants?name=test&page=1&size=10');
      expect(req.request.method).toBe('GET');
      req.flush(null, { status: 500, statusText: 'Internal Server Error' });

    }));
  });

  describe('create', () => {
    it('should create an accountant', () => {
      const mockAccountant: Accountant = {
        name: 'Test',
        registrationNumber: '123456',
        accountantCode: '123456',
        isActive: true
      };

      service.create(mockAccountant).subscribe((response:  Accountant) => {
        expect(response).toEqual(mockAccountant);
      });

      const req = httpMock.expectOne('http://localhost:8080/accountants');
      expect(req.request.method).toBe('POST');
      req.flush(mockAccountant);
    });
  });

  describe('edit', () => {
    it('should edit an accountant', () => {
      const mockAccountant: Accountant = {
        id: 1,
        name: 'Test',
        registrationNumber: '123456',
        accountantCode: '123456',
        isActive: true
      };

      service.edit(mockAccountant).subscribe((response:Accountant) => {
        expect(response).toEqual(mockAccountant);
      });

      const req = httpMock.expectOne('http://localhost:8080/accountants/1');
      expect(req.request.method).toBe('PUT');
      req.flush(mockAccountant);
    });
  });

  describe('delete', () => {
    it('should delete an accountant', () => {
      const accountantID = 1;

      service.delete(accountantID).subscribe((response:Accountant) => {
        expect(response).toEqual({});
      });

      const req = httpMock.expectOne('http://localhost:8080/accountants/1');
      expect(req.request.method).toBe('DELETE');
    });
  });

  describe('searchAccountByID', () => {
    it('should search an accountant by ID', () => {
      const accountantID = 1;
      const mockAccountant: Accountant = {
        id: 1,
        name: 'Test',
        registrationNumber: '123456',
        accountantCode: '123456',
        isActive: true
      };

      service.searchAccountByID(accountantID).subscribe((response:Accountant) => {
        expect(response).toEqual(mockAccountant);
      });

      const req = httpMock.expectOne('http://localhost:8080/accountants/1');
      expect(req.request.method).toBe('GET');
      req.flush(mockAccountant);
    });
  });
});