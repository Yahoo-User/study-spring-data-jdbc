package org.zerock.myapp.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data                       // DTO

// 지정된 테이블에 매핑되는 엔티티 클래스를 아래와 같이 선언함으로써
// 지정된 테이블에 대하여, CRUD & 목록조회를 가능하게 됩니다.
@Table(name = "person")     // JPA와 유사하게, 이 클래스로 매핑될 테이블명 지정
public class Person {
    // 1. PK 속성 선언
    @Id private Long id;    // PK 역할을 하는 필드에 @Id를 붙임으로써, PK지정

    // 2. 일반속성 선언
    private String name;
    private Integer age;


} // end class
