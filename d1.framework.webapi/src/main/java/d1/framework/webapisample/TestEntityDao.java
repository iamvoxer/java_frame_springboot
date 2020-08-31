package d1.framework.webapisample;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityDao extends JpaRepository<TestEntity, String> {

    TestEntity findByUserIdAndOperatorId(String userId, Integer operatorId);
}
