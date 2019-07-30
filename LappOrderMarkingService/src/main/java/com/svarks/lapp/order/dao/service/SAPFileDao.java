package com.svarks.lapp.order.dao.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.SAPFileInfo;


@Repository
public interface SAPFileDao extends JpaRepository<SAPFileInfo, Integer>  {

}
