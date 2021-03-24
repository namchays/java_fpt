package fpt.tracnghiem.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import fpt.tracnghiem.entity.DeThi;
import fpt.tracnghiem.entity.Lop;
import fpt.tracnghiem.entity.MonHoc;
import fpt.tracnghiem.model.ExamInformation;

@Repository
public interface DeThiRepository  extends JpaRepository<DeThi, Integer>{
	@Query("SELECT NEW fpt.tracnghiem.model.ExamInformation (a.idDe,a.tenDe, b.tenMonHoc, c.tenLop) FROM DeThi a INNER JOIN Lop c ON a.lop.idLop = c.idLop INNER JOIN MonHoc b ON a.monHoc.idMonHoc = b.idMonHoc")

	List<ExamInformation> getExamInformation();
	
	List<DeThi> findByTenDeContaining(String tenDe);
	List<DeThi> findByLop(Lop lop);
	List<DeThi> findByMonHoc(MonHoc monHoc);
	
}
