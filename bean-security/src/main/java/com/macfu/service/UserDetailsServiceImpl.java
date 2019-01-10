package com.macfu.service;


import com.macfu.dao.IMemberDAO;
import com.macfu.dao.IRoleDAO;
import com.macfu.po.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
@Service("userDetailsService") 	// 设置一个名称，此名称会被配置文件或者其它的类所引用
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private IMemberDAO memberDAO ;
	@Autowired
	private IRoleDAO roleDAO ;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Member> optional = this.memberDAO.findById(username) ; // 根据用户ID查询数据
		if (!optional.isPresent()) {	// 数据信息不存在
			throw new UsernameNotFoundException("用户“" + username + "”信息不存在，无法进行登录！");
		}	
		// 如果向下继续执行则表示用户信息存在，这个时候的用户信息不是SpringSecurity里面支持的信息，而是自定义对象
		Member member = optional.get() ; // 获取用户的信息 
		// 定义一个集合，保存所有的授权信息
		List<GrantedAuthority> allGrantedAuthority = new ArrayList<GrantedAuthority>() ;
		// 授权信息的内容需要通过IRoleDAO获取，根据用户名查询
		Set<String> allRoles = this.roleDAO.findAllByMember(username) ; // 查询所有的角色
		Iterator<String> roleIter = allRoles.iterator() ;
		while (roleIter.hasNext()) {	// 迭代保存角色信息
			allGrantedAuthority.add(new SimpleGrantedAuthority(roleIter.next())) ;	
		}
		boolean enabled = member.getEnabled().equals(1) ; // 启用状态
		UserDetails user = new User(username, member.getPassword(), enabled, true, true, true, allGrantedAuthority);
		return user ; 
	}
}
