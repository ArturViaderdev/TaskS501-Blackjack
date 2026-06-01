package cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.repository;

import cat.itacademy.s05.t01.n01.blackjack.infrastructure.output.mongo.document.GameDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpringDataGameMongoRepository extends ReactiveMongoRepository<GameDocument, String> {

}
