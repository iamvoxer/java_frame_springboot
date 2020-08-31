package d1.framework.webapisample;

import d1.framework.webapi.service.impl.HMACSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TestEntityService extends HMACSignService<TestEntity> {
    @Autowired
    TestEntityDao dao;

    @Override
    protected JpaRepository<TestEntity, String> getDao() {
        return dao;
    }

    public TestEntity findAAndB(String id, String userId, Integer operatorId) {
        boolean b = dao.existsById(id);
        return dao.findByUserIdAndOperatorId(userId, operatorId);
    }
}
