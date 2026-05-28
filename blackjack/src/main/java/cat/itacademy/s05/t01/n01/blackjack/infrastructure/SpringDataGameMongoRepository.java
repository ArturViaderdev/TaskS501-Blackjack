package cat.itacademy.s05.t01.n01.blackjack.infrastructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpringDataGameMongoRepository extends ReactiveMongoRepository<GameDocument, String> {

}
