package d1.project.sample.dao.user;

import d1.project.sample.entity.user.WebAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WebAdminUserDao extends JpaRepository<WebAdminUser, String> {

    WebAdminUser findByName(String name);

    boolean existsByName(String name);

    @Query(value = "select count(*) from d1_webadmin_user where (phone=?1 or name=?2) and id!=?3", nativeQuery = true)
    int findOtherUserByNameOrPhone(String phone, String name, String id);

}
