package org.zerock.myapp.runner;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.zerock.myapp.domain.Person;
import org.zerock.myapp.persistence.PersonRepository;
import org.zerock.myapp.util.PersonSeeder;

import java.util.*;


//@Log4j2
@Slf4j

@NoArgsConstructor

@Component
// Runner 클래스는, 스프링부트 프로젝트 수행시, 자동으로 콜백수행되는 클래스를 의미합니다.
// Runner 클래스는, 반드시 위와같이 @Component 어노테이션을 붙여서, Spring Context (즉,
// 스프링 빈즈 컨테이너)에 빈으로 자동등록 되어야만 자동실행됩니다.
public class CustomCmdLineRunner
    implements CommandLineRunner, InitializingBean, DisposableBean {
    @Autowired private PersonSeeder seeder;

    @Setter(onMethod_ = @Autowired)       // 주입시그널을 보내는 정석 어노테이션
    private PersonRepository dao;


    @Override
    public void afterPropertiesSet() { // 빈으로 초기화될 때
        log.trace("afterPropertiesSet() invoked.");

        assert this.seeder != null;
        log.info("\t+ this.seeder: {}", this.seeder);

        Objects.requireNonNull(this.dao);
        log.info("\t+ this.dao: {}", this.dao);
        log.info("\t+ type of dao: {}", this.dao.getClass().getName());
    } // afterPropertiesSet


    @Override
    public void run(String... args) {
        log.trace("run({}) invoked.", Arrays.toString(args));

        // Step1. PersonSeeder.prepareData 메소드 호출하여,
        //        빈 테이블에 데이터 입력
        this.seeder.prepareData();

        // 여기서부터, Spring Data JDBC 를 배웁니다.
        // PersonRepository 가 DAO 역할을 하도록 자동구현되고,
        // CrudRepository 인터페이스를 상속한 만큼, 이 인터페이스에 선언된
        // 다양한 CRUD & 목록조회 관련 메소드를 통해서, Person 테이블의 데이터를
        // 쉽게 사용할 수 있습니다.

        // Step2. 전체 데이터 목록조회
        Iterable<Person> iterable = this.dao.findAll();

        // 1st. method with external traverse, enhanced for statement.
//        for(Person person : iterable) {
//            log.info("\t+ person: {}", person);
//        } // enhanced for

        // 2nd. method with forEach method.
        iterable.forEach(person -> log.info(person.toString()));

        // Step3. 특정 엔티티 하나만 조회 with PK
        final Long pk = 7L;
        Optional<Person> foundPerson = this.dao.findById(pk);
        // 소위 값객체라 불리는, Optional<T> 객체는
        // 아래와 같이 값이 진짜 있을 때와, 없을 때를 구분해서
        // 코딩할 수 잇도록 해줍니다.
        foundPerson.ifPresent(p -> log.info(p.toString()));

        // Step4. 찾은 하나의 Person 객체를 삭제하자!!!
//        this.dao.delete(foundPerson.get());
        foundPerson.ifPresent(p -> {
//            this.dao.delete(p);               // 지정한 엔티티로 삭제
            this.dao.deleteById(p.getId());     // PK 속성값으로 삭제
        }); // .ifPresent

        // Step5. 테이블 전체 삭제
//        this.dao.deleteAll();

        // Step6. INSERT with an Entity
        // PK 속성값은 직접 지정하지 말아야 한다!
        Person newPerson = new Person();
        newPerson.setName("Yoseph");
        newPerson.setAge(23);

        this.dao.save(newPerson);

        // Step7. MODIFY with an found entity.
        foundPerson = this.dao.findById(8L);
        foundPerson.ifPresent(fp -> {
            fp.setName("MODIFIED");
            fp.setAge(120);

            this.dao.save(fp);  // Save or Modify
            log.info("\t+ ID: {} Entity Modified", fp.getId());
        });

        // Step8. Repository 인터페이스에 개발자가 직접 선언한 검색메소드 사용
        //        규칙: 검색메소드 선언시, findBy엔티티속성명(속성명) 규칙에
        //              맞게 선언해야 함.
        List<Person> foundPersons = this.dao.findByName("NAME");
        foundPersons.forEach(fp -> log.info("\t+ Found Person: {}", fp));

        // Step8. 엔티티의 이름속성을 부분검색
        List<Person> matchedPersons = this.dao.findByNameContaining("NAME");
        matchedPersons.forEach(mp -> log.info("\t+ Matched Person: {}", mp));

        // Step9. Repository 인터페이스에 직접 선언한 쿼리메소드에 대한 테스트
        Long searchId = 3L;
        String modifiedName = "Yoseph";

        // 쿼리메소드 호출
        boolean isUpdated = this.dao.updateNameById(searchId, modifiedName);
        log.info("\t+ isUpdated: {}", isUpdated);
    } // run

    @Override
    public void destroy() {    // 빈으로 파괴될 때
        log.trace("destroy() invoked.");
    } // destroy

} // end class


