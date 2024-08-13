package crude.tr.cadastroclientes;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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

    //Configurações do RabbitAdmin
    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> iniciarAdmin (RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    // Configurações do Exchange (fanout vai enviar para todas as filas ligadas a ele)
    @Bean
    public FanoutExchange createExchangeClient() {
        return ExchangeBuilder.fanoutExchange("cadastro-cliente-exchange").build();
    }

    // Configurações do Binding para ligar a fila ao exchange
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

    // Configurações do RabbitTemplate usando o Jackson2JsonMessageConverter pois eu vou enviar um objeto Cliente e o template padrão do RabbitMQ não sabe lidar com objetos
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
