package org.zerock.myapp.persistence;

// spring-data library
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.domain.Person;

import java.util.List;


// 이 Repository 인터페이스는 Spring-data 라이브러리에 의해서
// 런타임시(실행싯점)에 Dynamic Proxy 기법으로, Proxy 객체로 자동구현됨
@Repository // DAO 역할을 하는 인터페이스임을 알려주는 스트레오타입 어노테이션
            // 즉, Spring Context 에 자동으로 빈으로도 등록됨
public interface PersonRepository extends CrudRepository<Person, Long> {

    // Spring Data JDBC 에서 Repository 인터페이스에 일정한 규칙에 따라
    // 아래와 같이 선언가능한 명명법은 그 다음에 배우시는 Spring Data JPA에서
    // 다 해보도록 하겠습니다.
    public abstract List<Person> findByName(String searchName);
    public abstract List<Person> findByNameContaining(String searchName);


    // Step9. 직접 SQL문장을 붙여서, 원하는 메소드를 선언할 수 있습니다.
    //        이는 마치 MyBatis의 Mapper Interface에서, SQL을 어노테이션에
    //        넣어서 실행시켰던 방식과 아주 유사합니다.
    //        이렇게 선언된 메소드를, Spring Data JDBC에서는 "쿼리메소드"
    //        라고 부른다. 이유는 @Query(sql) 어노테이션으로 인해서, 결정된 이름입니다.
    //
    // *주의사항1: 바인드변수형식은 아래와 같이, ":바인드변수명"으로 지정
    // *주의사항2: 바인드변수에 매개변수의 값을 바인딩하려면, 아래와 같이
    //             @Param(바인드변수명) 어노테이션 사용해야만 합니다.
    // *주의사항3: 쿼리메소드의 이름은 여러분 맘대로 지어도 됩니다.
    // *주의사항4: @Modifying 어노테이션으로 쿼리메소드의 유형이 DML 문장임을 알려주셔야 합니다.


    @Modifying  // 아래의 쿼리메소드가 데이터를 DML 문장을 수행시킨다!!
    @Query("UPDATE person SET name = :name WHERE id = :id")
    public abstract boolean updateNameById(
            @Param("id") Long id,
            @Param("name") String name
    );

} // end interface
