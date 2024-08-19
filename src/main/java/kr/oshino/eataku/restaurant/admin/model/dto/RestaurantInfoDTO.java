package kr.oshino.eataku.restaurant.admin.model.dto;

import kr.oshino.eataku.common.enums.FoodType;
import kr.oshino.eataku.common.enums.HashTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RestaurantInfoDTO {

    private Long restaurantNo;
    private String restaurantName;
    private String contact;
    private String restaurantAddress;
    private Set<FoodType> foodTypes;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Set<HashTag> hashTags;
    private String description;
    private String postCode;
    private String address;
    private String detailAddress;
    private String extraAddress;
    private String imgUrl;
}
