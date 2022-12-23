package com.youtube.in28minutes.microservices.camelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//Rota que irá receber a fila criada no camel-microservice-a e processá-las
@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("activemq:my-activemq-queue")
                .to("log:received-message-from-active-mq");
    }
}
