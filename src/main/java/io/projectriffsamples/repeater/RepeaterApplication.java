package io.projectriffsamples.repeater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Collections;
import java.util.function.Function;

@SpringBootApplication
public class RepeaterApplication {

    @Bean
    public Function<Tuple2<Flux<String>, Flux<Integer>>, Flux<String>> bootRepeater() {
        return inputs -> {
            Flux<String> stringFlux = inputs.getT1();
            Flux<Integer> integerFlux = inputs.getT2();

            Flux<String> repeated = stringFlux.zipWith(integerFlux)
                    .flatMap(t -> Flux.fromIterable(Collections.nCopies(t.getT2(), t.getT1())));

            return repeated;
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(RepeaterApplication.class, args);
    }

}
