package com.shimonishi.keiichiro.tryjwt.mapper;

import com.shimonishi.keiichiro.tryjwt.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserEntity getUser(String username);
    int insert(UserEntity userEntity);
}
