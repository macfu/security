package com.macfu.dao;

import com.macfu.po.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * @Author: liming
 * @Date: 2019-01-09 13:52
 * @Description:
 */
public interface IRoleDAO extends JpaRepository<Role, String> {
    /**
     * 根据用户名查询出对应的所有角色的信息
     * @param mid 用户名
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT rid from member_role where mid=:mid")
    Set<String> findAllByMember(@Param("mid") String mid);
}
