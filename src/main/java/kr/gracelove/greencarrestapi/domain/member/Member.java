package kr.gracelove.greencarrestapi.domain.member;

import kr.gracelove.greencarrestapi.domain.BaseTimeEntity;
import kr.gracelove.greencarrestapi.domain.address.Address;
import kr.gracelove.greencarrestapi.web.dto.UpdateMemberRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    @Embedded
    private Address address;

    private String password;

    @Builder
    public Member(String name, String email, Address address, String password) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public void updateMember(UpdateMemberRequestDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.address = dto.getAddress();
        this.password = dto.getPassword();
    }


}
