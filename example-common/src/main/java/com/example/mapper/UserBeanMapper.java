package com.example.mapper;

import com.example.entity.bean.UserBean;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by binglin on 2016/10/7.
 */

@Mapper
@Repository
public interface UserBeanMapper {

    @Select("select * from user_bean where id = #{id}")
    UserBean findById(@Param("id") Integer id);

    @Select("select * from user_bean")
    List<UserBean> findAll();

    @Insert("insert into user_bean( name, password ) values ( #{name}, #{password} )")
    void save(UserBean userBean);
}
