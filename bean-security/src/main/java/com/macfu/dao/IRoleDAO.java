package com.macfu.dao;

import com.macfu.po.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface IRoleDAO extends JpaRepository<Role, String> {
	/**
	 * 根据用户名查询出对应的所有角色信息
	 * @param mid 用户名
	 * @return 用户角色集合 
	 */
	@Query(nativeQuery=true,
			value="SELECT rid FROM member_role WHERE mid=:mid")
	public Set<String> findAllByMember(@Param("mid") String mid) ;
}
