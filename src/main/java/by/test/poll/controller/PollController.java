package by.test.poll.controller;

import by.test.poll.entity.dto.OptionDtoName;
import by.test.poll.entity.dto.OptionDtoId;
import by.test.poll.entity.Poll;
import by.test.poll.entity.PollOption;
import by.test.poll.repository.PollOptionRepository;
import by.test.poll.repository.PollRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PollController {
    private PollRepository pollRepository;
    private PollOptionRepository pollOptionRepository;

    @Autowired
    public void setPollRepository(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Autowired
    public void setPollOptionRepository(PollOptionRepository pollOptionRepository) {
        this.pollOptionRepository = pollOptionRepository;
    }

    @ModelAttribute("poll-option")
    public PollOption writeOption() {
        return new PollOption();
    }

    @GetMapping("/polls")
    public String movie(ModelMap modelMap) {
        Iterable<Poll> polls = pollRepository.findAllByIsClosingNull();
        modelMap.put("polls", polls);
        return "polls";
    }

    @GetMapping("/add-poll")
    public String addPoll() {
        return "add_poll";
    }

    @GetMapping("/poll/{id}")
    public String getPollInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("poll", pollRepository.findOne(id));
        model.addAttribute("pollOption", pollOptionRepository.findByPollId(id));
        return "poll-info";
    }

    @PostMapping("/add-poll-name")
    @ResponseBody
    public void addPollInfo(@RequestBody Poll poll, HttpSession session) {
        session.setAttribute("poll", poll);
    }

    @PostMapping("/add-poll-options")
    @ResponseBody
    public void addPollOptions(@RequestBody String s, HttpSession session) {
        Type listType = new TypeToken<ArrayList<OptionDtoName>>(){}.getType();
        List<OptionDtoName> optionItems = new Gson().fromJson(s, listType);
        Poll poll = (Poll) session.getAttribute("poll");
        poll.setIsClosing(null);
        pollRepository.save(poll);
        optionItems
                .forEach(o -> pollOptionRepository.save(new PollOption(poll, o.getName(), 0L)));
    }

    @PostMapping("/poll-vote")
    @ResponseBody
    public void pollVote(@RequestBody String s) {
        Type listType = new TypeToken<ArrayList<OptionDtoId>>(){}.getType();
        List<OptionDtoId> optionItems = new Gson().fromJson(s, listType);

        List<PollOption> options = optionItems
                .stream()
                .map(o -> pollOptionRepository.findOne(o.getId()))
                .peek(o -> o.setNumberOfVoters(o.getNumberOfVoters() + 1))
                .collect(Collectors.toList());
        pollOptionRepository.save(options);
    }

    @GetMapping("vote-success")
    public String voteSuccess() {
        return "vote-success";
    }

    @GetMapping("/close-poll/{id}")
    public String closePoll(@PathVariable("id") Long id, Model model) {
        Poll poll = pollRepository.findOne(id);
        model.addAttribute("poll", poll);
        List<PollOption> options = pollOptionRepository.findByPollId(id);
        model.addAttribute("options", options);
        poll.setIsClosing('Y');
        pollRepository.save(poll);
        return "poll-result";
    }
}