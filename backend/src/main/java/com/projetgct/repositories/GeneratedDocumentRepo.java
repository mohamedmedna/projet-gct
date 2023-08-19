package com.projetgct.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projetgct.entities.GeneratedDocument;

@Repository
public interface GeneratedDocumentRepo extends JpaRepository<GeneratedDocument,Long> {

	    @Query("SELECT d FROM GeneratedDocument d JOIN d.servic s WHERE s.nomservice = :serviceName")
    List<GeneratedDocument> findByServiceName(@Param("serviceName") String serviceName);

}
