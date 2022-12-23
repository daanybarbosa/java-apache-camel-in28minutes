package com.youtube.in28minutes.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSenderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //timer
        //qual a frequencia da mensagem - 10000 milisegundos (10 segundos)
        //from("timer:active-mq-timer?timer=10000") //cronometro
        from("timer:active-mq-timer?period=10000") //periodo
        //mensagem para o ActiveMQ
                .transform().constant("My message for Active MQ")
                .log("${body}")
                .to("activemq:my-activemq-queue"); //enviar para o endpoint
        //queue
        //Acessar as queue - http://localhost:8161/admin/queues.jsp
    }
}
