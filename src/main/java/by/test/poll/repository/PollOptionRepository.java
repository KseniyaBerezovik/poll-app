package by.test.poll.repository;

import by.test.poll.entity.PollOption;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PollOptionRepository extends CrudRepository<PollOption, Long> {
    List<PollOption> findByPollId(Long id);
}
