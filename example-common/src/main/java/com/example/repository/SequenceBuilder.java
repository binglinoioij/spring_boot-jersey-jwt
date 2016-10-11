package com.example.repository;

import com.example.config.SpringContextHolder;
import com.example.entity.mongo.Sequence;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.inject.Inject;

/**
 * Created by binglin on 2016/9/16.
 */
public class SequenceBuilder {

    @Inject
    private static MongoTemplate mongoTemplate;

    static {
        mongoTemplate = SpringContextHolder.getBean("mongoTemplate");
    }

    public static long builder(String collName) {
        Query query = new Query(Criteria.where("_id").is(collName));
        Update update = new Update().inc("value", 1);
        Sequence seq = mongoTemplate.findAndModify(query, update, Sequence.class);
        if (null == seq) {
            mongoTemplate.save(new Sequence(collName));
            seq = mongoTemplate.findAndModify(query, update, Sequence.class);
        }
        return seq.getValue();
    }

}
