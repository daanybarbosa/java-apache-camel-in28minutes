package com.youtube.in28minutes.microservices.camelmicroservicea.route.a;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Exemplo 7
public class SimpleLoggingProcessor implements Processor {

    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        //logger.info("SimpleLoggingProcessor {}", exchange);
        logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
    }
}
