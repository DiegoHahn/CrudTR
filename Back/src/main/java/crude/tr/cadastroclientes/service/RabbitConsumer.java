package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);

    @RabbitListener(queues = "cadastro-cliente")
    public void receiveMessage(Client client) {
        logger.info("Mensagem recebida: {}", client);
    }
}