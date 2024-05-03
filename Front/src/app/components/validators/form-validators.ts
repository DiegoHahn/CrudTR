import { AbstractControl, ValidationErrors } from '@angular/forms';

export class FormValidators {

    static cpfValidator(control: AbstractControl): ValidationErrors | null {
        const cpf = control.value.replace(/\D/g, '');
          
        // Verificar se o CPF tem 11 dígitos
        if (cpf.length !== 11) {
            return { cpfInvalid: true };
        }
        
        // Verificar se todos os dígitos são
        // iguais (evitar CPFs como 111.111.111-11)
        if (/^(\d)\1+$/.test(cpf)) {
            return { cpfInvalid: true };
        }
        
        // Calcular os dígitos verificadores
        let soma = 0;
        for (let i = 0; i < 9; i++) {
            soma += parseInt(cpf.charAt(i)) * (10 - i);
        }
        
        let resto = 11 - (soma % 11);
        if (resto === 10 || resto === 11) {
            resto = 0;
        }
        
        if (resto !== parseInt(cpf.charAt(9))) {
            return { cpfInvalid: true };
        }
        
        soma = 0;
        for (let i = 0; i < 10; i++) {
            soma += parseInt(cpf.charAt(i)) * (11 - i);
        }
        
        resto = 11 - (soma % 11);
        if (resto === 10 || resto === 11) {
            resto = 0;
        }
        
        if (resto !== parseInt(cpf.charAt(10))) {
            return { cpfInvalid: true };
            }
        
        return null;
    }

    static cnpjValidator(control: AbstractControl): ValidationErrors | null {
        const cnpj = control.value.replace(/\D/g, '');
        
        // Verificar se o CNPJ tem 14 dígitos
        if (cnpj.length !== 14) {
            return { cnpjInvalid: true };
        }
        
        // Calcular os dígitos verificadores
        let tamanho = cnpj.length - 2;
        let numeros = cnpj.substring(0, tamanho);
        let digitos = cnpj.substring(tamanho);
        let soma = 0;
        let pos = tamanho - 7;
        
        for (let i = tamanho; i >= 1; i--) {
            soma += parseInt(numeros.charAt(tamanho - i)) * pos--;
            if (pos < 2) {
                pos = 9;
            }
        }
        
        let resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado !== parseInt(digitos.charAt(0))) {
            return { cnpjInvalid: true };
        }
        
        tamanho = tamanho + 1;
        numeros = cnpj.substring(0, tamanho);
        soma = 0;
        pos = tamanho - 7;
        
        for (let i = tamanho; i >= 1; i--) {
            soma += parseInt(numeros.charAt(tamanho - i)) * pos--;
            if (pos < 2) {
                pos = 9;
            }
        }
        
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado !== parseInt(digitos.charAt(1))) {
            return { cnpjInvalid: true };
        }
        
        return null;
    }
}
