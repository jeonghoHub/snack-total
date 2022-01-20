package jpabook.jpashop.service;

import jpabook.jpashop.repository.LoginRepository;
import jpabook.jpashop.snackDomain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    //로그인
    public List<User> login(User user) {
        List<User> findUser = loginRepository.login(user);
        if(findUser.isEmpty()){
            throw new IllegalStateException("not select User");
        }
        String[] a1 = new String[0], a2 = new String[0];

        a1.equals(a2);

        return findUser;
    }

    //회원 단건 조회
    public User findOne(String UserId){
        return loginRepository.findOne(UserId);
    }
}
