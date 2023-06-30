package com.projetgct.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projetgct.entities.Document;

@Repository
public interface DocumentRepo extends JpaRepository<Document,Long> {

    @Query("SELECT d FROM Document d JOIN d.servic s WHERE s.nomservice = :serviceName")
    List<Document> findByServiceName(@Param("serviceName") String serviceName);
	




}
