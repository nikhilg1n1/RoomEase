package com.roomeaseauth.Repository;

import com.roomeaseauth.DTO.RoomFilterDto;
import com.roomeaseauth.Entity.ListRooms;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RoomSpecification {

    public static Specification<ListRooms>apply(RoomFilterDto roomFilterDto){
            return (root, query, criteriaBuilder) ->{

                List<Predicate> predicates = new ArrayList<>();


                if(roomFilterDto.getRoomType() != null && !roomFilterDto.getRoomType().isEmpty()){
                    predicates.add(root.get("roomType").get("typeName").in(roomFilterDto.getRoomType()));
                }

                if(roomFilterDto.getMinRent() != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rent"),roomFilterDto.getMinRent()));
                }

                if(roomFilterDto.getMaxRent() != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rent"),roomFilterDto.getMaxRent()));
                }

                if(roomFilterDto.getOccupacy() != null && !roomFilterDto.getOccupacy().isEmpty()){
                    System.out.println("Filter Occupacy: " + roomFilterDto.getOccupacy());
                    Join<Object ,Object> occJoin = root.join("occupacyType");
                    predicates.add(occJoin.get("occupacy")
                            .in(roomFilterDto.getOccupacy()));
                }else {
                    System.out.println("Room Occupacy is:" + roomFilterDto.getOccupacy());
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
    }
}
