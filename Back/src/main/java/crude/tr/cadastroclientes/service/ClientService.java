package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.dto.ClientDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.model.Client;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import crude.tr.cadastroclientes.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
                    .orElseThrow(() -> new EntityNotFoundException("Contador não encontrado com o id: " + clientDTO.getAccountantId()));
            client.setAccountant(accountant);
        }
        return client;
    }

    public Page<Client> findClientsOrderedByName(String name, Pageable pageable) {
        return clientRepository.findClientsByName(name, pageable);
    }

    public Optional<Client> findCLientById(Long id) {
        return clientRepository.findById(id);
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
        if (client.getAccountant() != null) {
            Long accountantId = client.getAccountant().getId();
            Accountant accountant = accountantRepository.findById(accountantId)
                    .orElseThrow(() -> new EntityNotFoundException("Contador não encontrado com o id: " + accountantId));
            client.setAccountant(accountant);
        }
        return clientRepository.save(client);
    }

//    public ResponseEntity<Client> updateClient(Long id, ClientDTO clientDTO) {
//        Optional<Client> clientOptional = findCLientById(id);
//        if (clientOptional.isPresent()) {
//            Client client = convertToClient(clientDTO);
//            return new ResponseEntity<>(clientRepository.save(client), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    public ResponseEntity<Client> updateClient(Long id, ClientDTO clientDTO) {
        Optional<Client> clientOptional = findCLientById(id);
        //Atualiza o valor de um client do banco ao inves de criar um novo registro, devido ao funcionamento da JPA
        if (clientOptional.isPresent()) {
            Client existingClient = clientOptional.get();
            existingClient.setRegistrationType(clientDTO.getRegistrationType());
            existingClient.setRegistrationNumber(clientDTO.getRegistrationNumber());
            existingClient.setClientCode(clientDTO.getClientCode());
            existingClient.setName(clientDTO.getName());
            existingClient.setFantasyName(clientDTO.getFantasyName());
            existingClient.setRegistrationDate(DateUtil.convertStringToLocalDate(clientDTO.getRegistrationDate()));
            existingClient.setCompanyStatus(clientDTO.getCompanyStatus());
            if (clientDTO.getAccountantId() != null) {
                Accountant accountant = accountantRepository.findById(clientDTO.getAccountantId())
                        .orElseGet(() -> {
                            Accountant newAccountant = new Accountant();
                            // set properties of newAccountant based on clientDTO
                            return accountantRepository.save(newAccountant);
                        });
                existingClient.setAccountant(accountant);
            }
            return new ResponseEntity<>(clientRepository.save(existingClient), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteClient(Long id) {
        Optional<Client> clientOptional = findCLientById(id);
        if (clientOptional.isPresent()) {
            clientRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}