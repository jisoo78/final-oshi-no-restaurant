package kr.oshino.eataku.review.user.service;
import kr.oshino.eataku.list.entity.MyList;
import kr.oshino.eataku.member.entity.Member;
import kr.oshino.eataku.member.model.repository.MemberRepository;
import kr.oshino.eataku.reservation.user.repository.ReservationRepository;
import kr.oshino.eataku.restaurant.admin.entity.RestaurantInfo;
import kr.oshino.eataku.restaurant.admin.model.repository.RestaurantRepository;
import kr.oshino.eataku.review.user.entity.Review;
import kr.oshino.eataku.review.user.model.vo.CreateReviewUserRequestDto;
import kr.oshino.eataku.review.user.model.vo.CreateReviewUserResponseDto;
import kr.oshino.eataku.review.user.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class ReviewUserService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;




    /***
     * 리뷰 등록
     * @param createReviewUserRequestDto
     * @return
     */
    @Transactional
    public CreateReviewUserResponseDto insertReview(CreateReviewUserRequestDto createReviewUserRequestDto) {

        Member member = memberRepository.findById(createReviewUserRequestDto.getMemberNo())
                .orElseThrow(() -> new IllegalArgumentException("없는 회원 :" +createReviewUserRequestDto.getMemberNo()));

        RestaurantInfo restaurantInfo = restaurantRepository.findById((long) createReviewUserRequestDto.getRestaurantNo())
                .orElseThrow(()->new IllegalArgumentException("없는 식당" +createReviewUserRequestDto.getRestaurantNo()));


        Review review = Review.builder()
                .member(member)
                .restaurantInfo(restaurantInfo)
                .reviewContent(createReviewUserRequestDto.getReviewContent())
                .reviewTags(createReviewUserRequestDto.getReviewTags())
                .scope(createReviewUserRequestDto.getScope())
                .reviewNo(createReviewUserRequestDto.getReviewNo())
                .build();

        System.out.println("review = " + review);

        reviewRepository.save(review);

        return new CreateReviewUserResponseDto(200,"리뷰 작성 완료", 1L);

    }


    public List<CreateReviewUserRequestDto> getAllReviewByMember(Long loginedMemberNo) {

        return reviewRepository.findAllByMember_MemberNo(loginedMemberNo)
                .stream()
                .map(review -> CreateReviewUserRequestDto.builder()
                        .memberNo(review.getMember().getMemberNo())
                        .reviewNo(review.getReviewNo())
                        .reviewContent(review.getReviewContent())
                        .restaurantNo(review.getRestaurantInfo().getRestaurantNo())
                        .scope(review.getScope())
                        .reviewTags(review.getReviewTags())
                        .build())
                .collect(Collectors.toList());


    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(int reviewNo) {
        reviewRepository.deleteByReviewNo(reviewNo);
    }


    // 리뷰 수정
    @Transactional
    public void modifyReview(int reviewNo, String newReviewContent) {
        Review review = reviewRepository.findById((long) reviewNo)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다: " + reviewNo));

        review.setReviewContent(newReviewContent);

        reviewRepository.save(review);
    }


}
