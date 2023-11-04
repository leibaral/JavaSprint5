package cat.itacademy.s52.n12.JocDeDausMongoDB.models.services;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public long getNextSequence(String entityName) {
        Query query = new Query(Criteria.where("_id").is(entityName));
        Update update = new Update().inc("seq", 1);

        Counter counter = mongoTemplate.findAndModify(query, update, Counter.class);
        if (counter == null) {
            // Si no existeix l'entitat en la col·lecció, la creem.
            counter = new Counter();
            counter.setId(entityName);
            counter.setSeq(1);
            mongoTemplate.save(counter);
        }

        return counter.getSeq();
    }
}
