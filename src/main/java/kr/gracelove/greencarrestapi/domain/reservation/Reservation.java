package kr.gracelove.greencarrestapi.domain.reservation;

import kr.gracelove.greencarrestapi.domain.BaseTimeEntity;
import kr.gracelove.greencarrestapi.domain.car.Car;
import kr.gracelove.greencarrestapi.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Reservation extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private ReservationStatus status;

    @Builder
    public Reservation(Car car, Member member, ReservationStatus status) {
        this.car = car;
        this.member = member;
        this.status = status;
    }

    public void cancelReservation() {
        this.status = ReservationStatus.CANCEL;
    }
}
