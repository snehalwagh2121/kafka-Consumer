# kafka-Consumer

Dependency:
```java
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

Consumer properties:
```java
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    private static String KAFKA_SERVER = "localhost:9092";
    @Bean
    public ConsumerFactory<String, Employee> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "0");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserialize.class);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(Employee.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Employee> concurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Employee> factory= new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
```

Listener:
```java
@Component
public class Consumer {
    Gson g = new Gson();
    @KafkaListener(topics = "test", groupId = "0")
    public void consumer(String e){
        System.out.println("employee e = "+ e);
        Employee emp= g.fromJson(e, Employee.class);
        System.out.println(" employee object received: "+emp.getEid()+ ", "+emp.getEname()+", "+emp.getDept());
    }
}
```

Once we send a message to topic:
![img.png](img.png)