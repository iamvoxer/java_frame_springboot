package d1.project.sample.dao.user;

import d1.project.sample.entity.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppUserDao extends JpaRepository<AppUser, String> {

    AppUser findByName(String name);

    boolean existsByPhoneOrName(String phone, String name);

    @Query(value = "select count(*) from d1_app_user where (phone=?1 or name=?2) and id!=?3", nativeQuery = true)
    int findOtherUserByNameOrPhone(String phone, String name, String id);

}
