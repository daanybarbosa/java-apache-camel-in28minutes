package com.youtube.in28minutes.microservices.camelmicroservicea.route.a;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Camel é uma estrutura de integração
 * - queue - ouvir uma fila de qualquer entrada que vier
 * - transformation - fazer uma transformação/mudanças na mensagem que chegar
 * - database - salvar no banco de dados
 *
 * - timer - cronometro para disparar mensagens
 * - transformation
 * - log - escreve-los em um log
 */
@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

    @Override
    public void configure() throws Exception {
        //timer - cronometro para disparar mensagens
        //transformation
        //log - escreve-los em um log

        //from - sempre que for definir uma rota, deverá especificar o ponto inicial da rota
        //first-timer - nome do cronometro
        //endpoint
        //to - enviar

        //Exemplo 1
        //Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
        //Exchange[ExchangePattern: InOnly, BodyType: String, Body: [My constant message]]
        /*from("timer:first-timer") //queue - fila
                .transform().constant("My constant message") //transformar a mensagem em um valor constante - pegar a msg do endpoint do timer e transformar em uma contante
                .to("log:first-timer"); //database/log
        */

        // Exemplo 2
        /*from("timer: first-timer")
                .transform().constant("Time now is: " + LocalDateTime.now())
                .to("log:first-timer");
                */

        //Exemplo 3 - Bean
        /*from("timer:first-timer")
                .bean("getCurrentTimeBean")
                .to("log:first-timer"); */

        // Exemplo 4 - com autowired
        /*from("timer:first-timer")
                .bean(getCurrentTimeBean) //Autowired
                .to("log:first-timer"); */

        // Exemplo 5 - Usando componente GetCurrentTimeBean
        //Processing - receber uma mensagem e não alterar o corpo da mensagem
        //Transformation - receber uma mensagem e alterar o corpo da mensagem
        /*from("timer:first-timer")
                .log("${body}") //null
                .transform().constant("My constant message")
                .log("${body}") //My constant message
                .bean(getCurrentTimeBean)
                .log("${body}") //Time now is: 2022-12-21T00:45:35.898246
                .to("log:first-timer"); //Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time now is: 2022-12-21T00:45:35.898246]
         */

        // Exemplo 6 - Usando componente SimpleLoggingProcessingComponent
        /*from("timer:first-timer")
                .log("${body}") //null
                .transform().constant("My constant message")
                .log("${body}") //My constant message
                .bean(getCurrentTimeBean)
                .log("${body}") //Time now is: 2022-12-21T00:45:35.898246
                .bean(simpleLoggingProcessingComponent)
                .log("${body}") //SimpleLoggingProcessingComponent Time now is:
                .to("log:first-timer"); //Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time now is: 2022-12-21T00:45:35.898246]
*/

        //Exemplo 7
        from("timer:first-timer")
                .log("${body}") //null
                .transform().constant("My constant message")
                .log("${body}") //My constant message
                .bean(getCurrentTimeBean)
                .log("${body}") //Time now is: 2022-12-21T00:45:35.898246
                .bean(simpleLoggingProcessingComponent)
                .log("${body}") //SimpleLoggingProcessingComponent Time now is:
                //.process(new SimpleLoggingProcessor()) //SimpleLoggingProcessor Exchange[]
                .process(new SimpleLoggingProcessor()) //SimpleLoggingProcessor Time now is: 2022-12-21T01:13:06.785044
                .to("log:first-timer"); //Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time now is: 2022-12-21T00:45:35.898246]

    }
}

//Exemplo 3 - Bean
@Component
class GetCurrentTimeBean {
    public String getCurrentTime(){
        return "Time now is: " + LocalDateTime.now();
    }
}

// Exemplo 6
@Component
class SimpleLoggingProcessingComponent {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message){
        logger.info("SimpleLoggingProcessingComponent {}", message);
    }
}

