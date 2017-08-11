package by.test.poll.repository;


import by.test.poll.entity.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {
    Iterable<Poll> findAllByIsClosingNull();
}
