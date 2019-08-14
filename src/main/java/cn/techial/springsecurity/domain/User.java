package cn.techial.springsecurity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author techial
 */
@Entity
@Data
@Accessors(chain = true)
@JsonIgnoreProperties("password")
public class User {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id = -1;

    /**
     * 用户名
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * 微信 id
     */
    @OneToOne
    private UserWeChat weChat;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    private Boolean enabled = true;

    public UserPrincipal toUserPrincipal() {
        return new UserPrincipal(
            id, username, password, enabled, true, true, true,
            roles.stream().map(it -> new SimpleGrantedAuthority(it.name())).collect(Collectors.toSet())
        );
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof User) {
            final User user = (User) o;
            return user.getId().equals(this.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
