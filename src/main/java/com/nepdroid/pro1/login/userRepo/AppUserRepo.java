package com.nepdroid.pro1.login.userRepo;

import com.nepdroid.pro1.login.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser,Integer> {
    AppUser findByEmail(String email);
}
