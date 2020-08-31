package d1.framework.webapisample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface WebAdminUserDao extends JpaRepository<WebAdminUser, String> {

    WebAdminUser findByName(String name);

    boolean existsByName(String name);

    @Query(value = "select count(*) from d1_webadmin_user where (phone=?1 or name=?2) and id!=?3", nativeQuery = true)
    int findOtherUserByNameOrPhone(String phone, String name, String id);

    @Query(value = "select count(*) from d1_webadmin_user a, d1_my_entity b where a.id=b.user_id", nativeQuery = true)
    int findSame1();

    @Query(value = "select a.id,b.opearte_desc from d1_webadmin_user a, d1_my_entity b where a.id = b.user_id", nativeQuery = true)
    List<Map<String, Object>> findSame2();
}
