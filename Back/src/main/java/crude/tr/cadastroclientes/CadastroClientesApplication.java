package crude.tr.cadastroclientes;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class CadastroClientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroClientesApplication.class, args);
	}
}
