package by.test.poll.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "polls_options")
public class PollOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @Column(name = "option_content")
    private String content;

    @Column(name = "number_of_voters")
    private Long numberOfVoters;

    public PollOption(Poll poll, String content, Long numberOfVoters) {
        this.poll = poll;
        this.content = content;
        this.numberOfVoters = numberOfVoters;
    }
}