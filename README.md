# Aprendendo Apache Camel Framework com Spring Boot
### Youtube
**Canal:** in28minutes - Cloud Made Easy - **link:** https://www.youtube.com/watch?v=eh9C0GyxtHE&t=6s

## Dependências
- Spring Boot Actuator
- Spring Boot Web
- Apache Camel
- Spring Boot DevTools
- Apache Camel ActiveMQ

## Passo a Passo
### Getting Started with Apache Camel and Spring Boot

- Step 01 - Getting Started with Apache Camel and Enterprise Integration
- Step 02 - Creating Microservices for playing with Apache Camel
- Step 03 - Creating your first Apache Camel Route
- Step 04 - Using Spring Beans for Transformation in Camel Routes
- Step 05 - Processing using Camel Processors in Camel Routes
- Step 06 - Creating a Camel Route to play with Files

### Integrating Apache Camel with ActiveMQ and Kafka
- Step 01 - Launch ActiveMQ as a Docker Container
- Step 02 - Creating Sender Camel Route for ActiveMQ in Microservice A
- Step 03 - Creating Receiver Camel Route for ActiveMQ in Microservice B
- Step 04 - Understanding Camel Terminology and Architecture
- Step 05 - Unmarshalling JSON Message to Java Bean in ActiveMQ Camel Route

## Comandos
### Versão do docker
```
$ docker --version
```

### Iniciando o Active MQ
```
$ docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
```

### Acesso ao Active MQ
- Clicar em:
  - Manage ActiveMQ broker

- Link: ActiveMQ WebConsole available at `http://0.0.0.0:8161/` ou `http://localhost:8161`
- Usuario: `admin`
- Senha: `admin`

### Criando Key Store
```
$ keytool -genseckey -alias myDesKey -keypass someKeyPassword -keystore myDesKey.jceks -storepass someKeystorePassword -v -storetype JCEKS -keyalg DES
```

### Método para ler o Key Store
```
private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
		CertificateException, UnrecoverableKeyException {
	KeyStore keyStore = KeyStore.getInstance("JCEKS");
	ClassLoader classLoader = getClass().getClassLoader();
	keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
	Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());
	CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
	return sharedKeyCrypto;
} 
```

### ArrayListAggregationStrategy
```
class ArrayListAggregationStrategy implements AggregationStrategy {
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Object newBody = newExchange.getIn().getBody();
        ArrayList<Object> list = null;
        if (oldExchange == null) {
            list = new ArrayList<Object>();
            list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(newBody);
            return oldExchange;
        }
    }
}
```

## Diagrams

```
digraph architecture {
rankdir = LR;
node[shape="rect"]
node[style=filled,color="#59C8DE"];
{rank=same; Microservice2, Microservice3}
{rank=same; Microservice1, Microservice6, RabbitMQ3}
RabbitMQ1,RabbitMQ3,RabbitMQ4,RabbitMQ5[shape=underline,style=unfilled,color="#000000",label=<Queue>]
Microservice1 -> RabbitMQ1 -> Microservice2
Microservice1 -> Microservice3
Microservice1 -> RabbitMQ3 -> Microservice6
Microservice3 -> RabbitMQ4 -> Microservice4
Microservice2 -> RabbitMQ5 -> Microservice5
}
digraph architecture {
rankdir = LR;
node[shape="rect"]
node[style=filled,color="#59C8DE"];
Microservice1 -> Microservice2
Microservice1 -> Microservice3
Microservice1 -> Microservice4
}
digraph architecture {
rankdir = LR;
node[shape="rect"]
node[style=filled,color="#59C8DE"];
Microservice1 -> Microservice4
Microservice2 -> Microservice4
Microservice3 -> Microservice4
Microservice4 -> Microservice5
}
digraph architecture {
rankdir = LR;
node[shape="rect"]
node[style=filled,color="#59C8DE"];
Microservice1 -> Microservice4
Microservice2 -> Microservice4
Microservice3 -> Microservice4
Microservice4 -> Microservice5
Microservice4 -> Microservice6
Microservice6 -> Microservice7
Microservice6 -> Microservice8
Microservice8 -> Microservice4
}
digraph architecture {
rankdir = LR;
node[shape=component]
node[style=filled,color="#59C8DE"];
EndPoint1  -> Consumer
EndPoint2  -> Consumer
EndPoint3  -> Consumer
}
digraph architecture {
rankdir = LR;
node[shape=component]
node[style=filled,color="#59C8DE"];
Producer -> EndPoint1
Producer  -> EndPoint2
Producer  -> EndPoint3
}
digraph architecture {
rankdir = LR;
node[style=filled,color="#59C8DE",shape="rect",width=1.5];
Exchange -> ExchangeId
Exchange  -> MEP
Exchange  -> Properties
Exchange  -> Input
Exchange  -> Output
}
digraph G {
rankdir = TB;
    
node[style=filled,color="#59C8DE",shape="rect",width=1];
MicroserviceA -> Queue
Queue2 -> MicroserviceC
Queue, Queue2[shape=underline,style=unfilled,color="#000000",label=<Queue>]
Database[shape=cylinder]
	
	subgraph cluster_1 {
	    label = "MicroserviceB";
		EndPoint1 -> Processor -> EndPoint2
		Processor -> EndPoint3 
	}
    EndPoint2 -> Database
	Queue -> EndPoint1
	EndPoint3 -> Queue2
    {rank=same; Database, Queue2}
}
```