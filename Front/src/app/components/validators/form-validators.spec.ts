import { FormValidators } from './form-validators';
import { FormControl } from '@angular/forms';

describe('FormValidators', () => {

  describe('cpfValidator', () => {
    it('should return null for a valid CPF', () => {
      const control = new FormControl('123.456.789-09');
      expect(FormValidators.cpfValidator(control)).toBeNull();
    });

    it('should return { cpfInvalid: true } for a CPF with incorrect length', () => {
      const control = new FormControl('123.456.789');
      expect(FormValidators.cpfValidator(control)).toEqual({ cpfInvalid: true });
    });

    it('should return { cpfInvalid: true } for a CPF with all identical digits', () => {
      const control = new FormControl('111.111.111-11');
      expect(FormValidators.cpfValidator(control)).toEqual({ cpfInvalid: true });
    });

    it('should return { cpfInvalid: true } for a CPF with incorrect 10th digit', () => {
        const control = new FormControl('123.456.789-90');
        expect(FormValidators.cpfValidator(control)).toEqual({ cpfInvalid: true });
    });

    // it('should return null for a valid CPF where the remainder is 10 or 11', () => {
    //     const control = new FormControl('220.512.176-08'); 
    //     expect(FormValidators.cpfValidator(control)).toBeNull();
    //   });

    it('should return { cpfInvalid: true } for a CPF with incorrect check digits', () => {
      const control = new FormControl('123.456.789-00');
      expect(FormValidators.cpfValidator(control)).toEqual({ cpfInvalid: true });
    });
  });

  describe('cnpjValidator', () => {
    it('should return null for a valid CNPJ', () => {
      const control = new FormControl('12.345.678/0001-95');
      expect(FormValidators.cnpjValidator(control)).toBeNull();
    });

    it('should return { cnpjInvalid: true } for a CNPJ with incorrect length', () => {
      const control = new FormControl('12.345.678/0001');
      expect(FormValidators.cnpjValidator(control)).toEqual({ cnpjInvalid: true });
    });

    it('should return { cnpjInvalid: true } for a CNPJ with incorrect check digits', () => {
      const control = new FormControl('12.345.678/0001-00');
      expect(FormValidators.cnpjValidator(control)).toEqual({ cnpjInvalid: true });
    });
  });
});
