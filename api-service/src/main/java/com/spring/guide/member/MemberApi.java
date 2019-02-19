package com.spring.guide.member;

import com.spring.guide.common.resonse.Existence;
import com.spring.guide.domain.member.Member;
import com.spring.guide.domain.member.MemberHelperService;
import com.spring.guide.member.dto.MemberProfileUpdate;
import com.spring.guide.member.dto.MemberResponse;
import com.spring.guide.member.dto.SignUpRequest;
import com.spring.guide.member.service.MemberProfileService;
import com.spring.guide.member.service.MemberSignUpService;
import com.spring.guide.member.type.MemberExistenceType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberSignUpService memberSignUpService;
    private final MemberHelperService memberHelperService;
    private final MemberProfileService memberProfileService;
    private final MemberSearchService memberSearchService;

    @PostMapping
    public MemberResponse create(@RequestBody @Valid final SignUpRequest dto) {
        final Member member = memberSignUpService.doSignUp(dto);
        return new MemberResponse(member);
    }

    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable long id) {
        return new MemberResponse(memberHelperService.findById(id));
    }

    @PutMapping("/{id}/profile")
    public void updateProfile(@PathVariable long id, @RequestBody @Valid final MemberProfileUpdate dto) {
        memberProfileService.update(id, dto);
    }

    @GetMapping("/existence")
    public Existence isExistTarget(
            @RequestParam("type") final MemberExistenceType type,
            @RequestParam(value = "value", required = false) final String value
    ) {
        return new Existence(memberSearchService.isExistTarget(type, value));
    }

}
