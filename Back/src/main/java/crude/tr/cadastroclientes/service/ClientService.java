package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.dto.ClientDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.model.Client;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import crude.tr.cadastroclientes.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final AccountantRepository accountantRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, AccountantRepository accountantRepository) {
        this.clientRepository = clientRepository;
        this.accountantRepository = accountantRepository;
    }

    public Client convertToClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setRegistrationType(clientDTO.getRegistrationType());
        client.setRegistrationNumber(clientDTO.getRegistrationNumber());
        client.setClientCode(clientDTO.getClientCode());
        client.setName(clientDTO.getName());
        client.setFantasyName(clientDTO.getFantasyName());
        client.setRegistrationDate(DateUtil.convertStringToLocalDate(clientDTO.getRegistrationDate()));
        client.setCompanyStatus(clientDTO.getCompanyStatus());
        //Passar o ID diretamente na requisição, pode ser que eu precise passar o objeto inteiro do front-end
        if (clientDTO.getAccountantId() != null) {
            Accountant accountant = accountantRepository.findById(clientDTO.getAccountantId())
                    .orElseThrow(() -> new EntityNotFoundException("Accountant not found with id: " + clientDTO.getAccountantId()));
            client.setAccountant(accountant);
        }
        return client;
    }

    //Conversor de Datas (é aqui o lugar dessa classe?)
    public static class DateUtil {

        public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        public static LocalDate convertStringToLocalDate(String dateStr) {
            return LocalDate.parse(dateStr, FORMATTER);
        }

        public static String formatLocalDateToString(LocalDate date) {
            if (date == null) {
                return null;
            }
            return date.format(FORMATTER);
        }
    }

    //Salva um cliente associado a um contador
    public Client addClient(Client client) {
        //verifica se está sendo passado um contador no corpo da requisição
        if (client.getAccountant() != null && client.getAccountant().getAccountantCode() != null) {
            Long accountantId = client.getAccountant().getId(); // PERGUNTAR SE DEVE SALVAR O ID OU O accountantCode
            Accountant accountant = accountantRepository.findById(accountantId)
                    .orElseThrow(() -> new EntityNotFoundException("Accountant not found with id: " + accountantId));
            client.setAccountant(accountant);
        }
        return clientRepository.save(client);
    }
}