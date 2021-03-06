package kr.gracelove.greencarrestapi.web.dto;

import kr.gracelove.greencarrestapi.domain.address.Address;
import kr.gracelove.greencarrestapi.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String name;
    private String email;
    private Address address;


    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.address = member.getAddress();
    }
}
