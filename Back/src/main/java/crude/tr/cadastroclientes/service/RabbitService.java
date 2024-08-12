package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.model.Client;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

    private RabbitTemplate rabbitTemplate;

    public void createClient(Client client, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", client);
    }
}
