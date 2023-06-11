package com.spring.aws.config;

import com.spring.aws.domain.Character;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class AWSLambdaConfig {

    //Supplier no reciben nada y retornan algo
    @Bean(name = "Saludo")
    public Supplier<String> greeting(){//se recomienda usar con peticiones GET
        return ()->"Hello World";
    }

    //Consumer reciben algo y no retornan nada
    @Bean(name = "ConsumeParam")
    public Consumer<String> printParam(){//se recomienda usar con peticiones POST, aunque GET sirve
        return (param)->{
            System.out.println(param);
        };
    }

    //Function reciben algo y retornan algo
    @Bean(name = "duplo")
    public Function<Integer,String> duplicar(){//da igual GET o POST
        return (number)->{
          String result = "El duplo es: "+new Integer(number)*2;
          return result.toUpperCase();
        };
    }

    @Bean(name = "jsonPerson")
    public Supplier<Map<String,Object>> person(){
        return ()->{
          Map<String,Object> personJson = new HashMap<>();
          personJson.put("name","Adriel Alejandro");
          personJson.put("apellidos","Aliaga Benavides");
          personJson.put("edad",37);
          return personJson;
        };
    }

    @Bean(name = "personToString")
    public Function<Map<String,Object>,String> personToString(){
        return (jsonMap)->{
            AtomicReference<String> person = new AtomicReference<>("");
            jsonMap.forEach((key,value)->{
                person.set(person.get()+(key+": "+value+"\n"));
            });
            return person.get();
        };
    }

    @Bean
    public Function<Character,Character> aunmentarVida(){
        return (character)->{
            character.setHealthPoints(character.getHealthPoints()+100);
            return character;
        };
    }

    @Bean
    public SecurityFilters filters(){
        return new SecurityFilters();
    }

}
