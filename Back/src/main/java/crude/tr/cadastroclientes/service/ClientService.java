package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.Exceptions.AccountantNotFoundException;
import crude.tr.cadastroclientes.Exceptions.ClientNotFoundException;
import crude.tr.cadastroclientes.dto.ClientDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.model.Client;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import crude.tr.cadastroclientes.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

public Client convertToClient(ClientDTO clientDTO) throws AccountantNotFoundException {
    Client client = new Client();
    client.setRegistrationType(clientDTO.getRegistrationType());
    client.setRegistrationNumber(clientDTO.getRegistrationNumber());
    client.setClientCode(clientDTO.getClientCode());
    client.setName(clientDTO.getName());
    client.setFantasyName(clientDTO.getFantasyName());
    client.setRegistrationDate(clientDTO.getRegistrationDate());
    client.setCompanyStatus(clientDTO.getCompanyStatus());

    Long accountantId = clientDTO.getAccountantId();

    if (accountantId != null ) {
        Accountant accountant = accountantRepository.findById(accountantId)
                .orElseThrow(() -> new AccountantNotFoundException("Contador não encontrado com o id: " + accountantId));
        client.setAccountant(accountant);
    } else {
        // Caso o accountantId não seja válido
        throw new AccountantNotFoundException("Você deve informar um contador válido");
    }
    return client;
}

    public Page<Client> findClientsOrderedByName(String name, Pageable pageable) {
        return clientRepository.findClientsByName(name, pageable);
    }

    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }

    //Salva um cliente associado a um contador
    public Client addClient(Client client) throws AccountantNotFoundException {
        //verifica se está sendo passado um contador no corpo da requisição
        if (client.getAccountant() != null && client.getAccountant().getId() != null) {
            Long accountantId = client.getAccountant().getId();
            Accountant accountant = accountantRepository.findById(accountantId)
                    .orElseThrow(() -> new AccountantNotFoundException("Contador não encontrado com o id: " + accountantId));
            client.setAccountant(accountant);
        }
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, ClientDTO clientDTO) throws ClientNotFoundException, AccountantNotFoundException {
        Client existingClient = findClientById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado com o id: " + id));

        existingClient.setRegistrationType(clientDTO.getRegistrationType());
        existingClient.setRegistrationNumber(clientDTO.getRegistrationNumber());
        existingClient.setClientCode(clientDTO.getClientCode());
        existingClient.setName(clientDTO.getName());
        existingClient.setFantasyName(clientDTO.getFantasyName());
        existingClient.setRegistrationDate(clientDTO.getRegistrationDate());
        existingClient.setCompanyStatus(clientDTO.getCompanyStatus());

        if (clientDTO.getAccountantId() != null) {
            Accountant accountant = accountantRepository.findById(clientDTO.getAccountantId())
                    .orElseThrow(() -> new AccountantNotFoundException("Contador não encontrado com o id: " + clientDTO.getAccountantId()));
            existingClient.setAccountant(accountant);
        }

        return clientRepository.save(existingClient);
    }

    public void deleteClient(Long id) throws ClientNotFoundException {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException("Cliente não encontrado com o id: " + id);
        }
        clientRepository.deleteById(id);
    }
}