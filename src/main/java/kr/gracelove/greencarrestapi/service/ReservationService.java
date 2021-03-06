package kr.gracelove.greencarrestapi.service;

import kr.gracelove.greencarrestapi.domain.car.Car;
import kr.gracelove.greencarrestapi.domain.car.CarRepository;
import kr.gracelove.greencarrestapi.domain.car.CarStatus;
import kr.gracelove.greencarrestapi.domain.car.exception.CarNotAvailableException;
import kr.gracelove.greencarrestapi.domain.car.exception.CarNotFoundException;
import kr.gracelove.greencarrestapi.domain.member.Member;
import kr.gracelove.greencarrestapi.domain.member.MemberRepository;
import kr.gracelove.greencarrestapi.domain.member.exception.MemberNotFoundException;
import kr.gracelove.greencarrestapi.domain.reservation.Reservation;
import kr.gracelove.greencarrestapi.domain.reservation.ReservationRepository;
import kr.gracelove.greencarrestapi.domain.reservation.ReservationStatus;
import kr.gracelove.greencarrestapi.domain.reservation.exception.ReservationNotFoundException;
import kr.gracelove.greencarrestapi.web.dto.ReservationRequestDto;
import kr.gracelove.greencarrestapi.web.dto.ReservationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final CarRepository carRepository;

    public Long reservation(ReservationRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(() -> new MemberNotFoundException(requestDto.getMemberId()));

        Car car = carRepository.findById(requestDto.getCarId()).orElseThrow(() -> new CarNotFoundException(requestDto.getCarId()));

        if(car.getStatus() != CarStatus.AVAILABLE) throw new CarNotAvailableException(car.getId());

        Reservation reservation = Reservation.builder()
                .status(ReservationStatus.RESERVATION)
                .car(car)
                .member(member).build();

        car.changeStatus(CarStatus.RESERVED);

        reservationRepository.save(reservation);
        return reservation.getId();
    }

    @Transactional(readOnly = true)
    public ReservationResponseDto getReservation(Long id) {
        return reservationRepository.findById(id).map(ReservationResponseDto::new).orElseThrow( () -> new ReservationNotFoundException(id) );
    }

    @Transactional(readOnly = true)
    public Page<ReservationResponseDto> getReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable).map(ReservationResponseDto::new);
    }

    public Long cancel(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow( () -> new ReservationNotFoundException(id) );
        reservation.cancelReservation();
        Car car = reservation.getCar();
        car.changeStatus(CarStatus.AVAILABLE);

        return reservation.getId();
    }


}
