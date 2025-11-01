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
                    predicates.add(criteriaBuilder.equal(root.get("roomType"),roomFilterDto.getRoomType()));
                }

                if(roomFilterDto.getMinRent() != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rent"),roomFilterDto.getMinRent()));
                }

                if(roomFilterDto.getMaxRent() != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rent"),roomFilterDto.getMaxRent()));
                }

                if(roomFilterDto.getOccupacyType() != null && !roomFilterDto.getOccupacyType().isEmpty()){
                    Join<Object ,Object> occJoin = root.join("occupacyType");
                    predicates.add(criteriaBuilder.equal(occJoin.get("occupacy"),roomFilterDto.getOccupacyType()));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
    }
}
