package com.youtube.in28minutes.microservices.camelmicroservicea.routes.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFileRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:files/input") //<arquivo>:<especificar_caminho>/<pasta_de_entrada>
                .log("${body}") //exibe o conteudo do body
                .to("file:files/output");
    }
}
