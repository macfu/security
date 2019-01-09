package com.macfu.dao;

import com.macfu.po.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMemberDAO extends JpaRepository<Member, String> {
}
