package fpt.tracnghiem.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fpt.tracnghiem.entity.CauHoi;
import fpt.tracnghiem.entity.DeThi;

@Repository
public interface CauHoiRepository extends JpaRepository<CauHoi, Integer> {
	Page<CauHoi> findAllByDeThi(DeThi deThi, Pageable pageable);
	List<CauHoi> findAllByDeThi(DeThi deThi);
}
