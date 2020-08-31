package d1.framework.webapisample;

import d1.framework.webapi.service.impl.DoServiceImpBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class MyService extends DoServiceImpBase<MyEntity> {
    @Autowired
    MyDao dao;

    @Override
    protected JpaRepository<MyEntity, String> getDao() {
        return dao;
    }

    public void test1() {

    }
}
