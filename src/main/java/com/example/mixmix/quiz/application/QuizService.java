package net.skhu.quiz.application;

import com.example.mixmix.ai.dto.AiResponseDto;
import com.example.mixmix.member.domain.Member;
import com.example.mixmix.member.domain.repository.MemberRepository;
import com.example.mixmix.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final ChatClient chatClient;
    private final MemberRepository memberRepository;

    @Value("${questions.korea}")
    private String koreaQuestions;

    @Value("${questions.global}")
    private String globalQuestions;

    @Transactional
    public AiResponseDto askForAdvice(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        String selectedQuestions = member.getNationality().equalsIgnoreCase("korea") ? koreaQuestions : globalQuestions;

        ChatResponse response = callChat(selectedQuestions);
        if (response == null) {
            response = callChat(selectedQuestions);
        }

        return AiResponseDto.builder()
                .answer(response.getResult().getOutput().getContent()).build();
    }

    private ChatResponse callChat(String prompt) {
        return chatClient.call(
                new Prompt(
                        prompt,
                        OpenAiChatOptions.builder()
                                .withTemperature(0.4F)
                                .withFrequencyPenalty(0.7F)
                                .withModel("gpt-4o")
                                .build()
                ));
    }

    @Transactional
    public boolean chooseTheCorrectAnswer(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        member.incrementStreak();

        return true;
    }

    public boolean chooseTheIncorrectAnswer() {
        return false;
    }
}
