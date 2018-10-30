package com.haixia.mapper;

import com.haixia.pojo.User;

public interface IUserMapper {
    int deleteById(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User getById(Integer userId);
    
    User getByUserName(String userName);
    
    User getByUserPhone(String userrPhone);

    int updateByIdSelective(User record);

    int updateById(User record);
    
}