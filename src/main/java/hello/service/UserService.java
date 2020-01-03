package hello.service;

import hello.model.Permission;
import hello.model.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    /**
     * 重寫loadUserByUsername 方法獲得 userdetails 型別使用者
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = new SysUser();
        user.setId(1000001);
        user.setUsername("user");
        user.setPassword("password");
        if (user != null) {
            List<Permission> permissions = new ArrayList<>();
            Permission p = new Permission();
            p.setName("USER");
            permissions.add(p);

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissions) {
                if (permission != null && permission.getName() != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    //1：此處將許可權資訊新增到 GrantedAuthority 物件中，在後面進行全許可權驗證時會使用GrantedAuthority 物件。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }
}
