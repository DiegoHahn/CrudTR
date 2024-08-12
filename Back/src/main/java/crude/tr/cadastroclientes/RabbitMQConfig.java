package crude.tr.cadastroclientes;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue createQueueClient() {
        return QueueBuilder.durable("cadastro-cliente").build();
    }

    @Bean
    public Queue createQueueAccountant() {
        return QueueBuilder.durable("cadastro-contador").build();
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> iniciarAdmin (RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange createExchangeClient() {
        return ExchangeBuilder.fanoutExchange("cadastro-cliente-exchange").build();
    }

    @Bean
    public Binding createBinding() {
        return BindingBuilder.bind(createQueueClient()).to(createExchangeClient());
    }

    @Bean
    public FanoutExchange createExchangeAccountant() {
        return ExchangeBuilder.fanoutExchange("cadastro-contador-exchange").build();
    }

    @Bean
    public Binding createBindingAccountant() {
        return BindingBuilder.bind(createQueueAccountant()).to(createExchangeAccountant());
    }
}
