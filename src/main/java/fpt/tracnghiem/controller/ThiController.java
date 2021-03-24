package fpt.tracnghiem.controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fpt.tracnghiem.config.MyConstances;
import fpt.tracnghiem.entity.CauHoi;
import fpt.tracnghiem.entity.DeThi;
import fpt.tracnghiem.entity.Lop;
import fpt.tracnghiem.entity.MonHoc;
import fpt.tracnghiem.entity.PhuongAn;
import fpt.tracnghiem.entity.TaiKhoan;
import fpt.tracnghiem.entity.ThamGiaThi;
import fpt.tracnghiem.model.KetQuaBaiThi;
import fpt.tracnghiem.entity.Role;
import fpt.tracnghiem.service.CauHoiService;
import fpt.tracnghiem.service.DeThiService;
import fpt.tracnghiem.service.LopService;
import fpt.tracnghiem.service.MonHocService;
import fpt.tracnghiem.service.ThiService;
import fpt.tracnghiem.service.RoleService;
import fpt.tracnghiem.service.TaiKhoanService;

// TODO: Auto-generated Javadoc
/**
 * The Class ThiController.
 */
@Controller
@RequestMapping(value = "/user")
public class ThiController {
	
	/** The cau hoi service. */
	@Autowired
	private CauHoiService cauHoiService;
	
	/** The dethi service. */
	@Autowired
	private DeThiService dethiService;

	@Autowired
	private TaiKhoanService taiKhoanService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private LopService lopService;
	
	/** The mon hoc service. */
	@Autowired
	private MonHocService monHocService;
	
	@Autowired
	private ThiService thiService;

	@Autowired
	private TaiKhoanService taiKhoanService;
	
	/**
	 * Load danh sách các đề thi
	 *
	 * @param req the req
	 * @param model the model
	 * @param page the page
	 * @return the string
	 */
	@RequestMapping(value = "/thi/page/{page}",method = RequestMethod.GET)
	public String userInfo(HttpServletRequest req, Model model,@PathVariable(name = "page",required = false) Integer page) {
		if(page == null || page < 1) {
			page=1;
		}
		Page<DeThi> pageDethi = dethiService.findPaginated(page, MyConstances.HOMEPAGE_SIZE);
		List<DeThi> dsDethi = pageDethi.getContent();
		List<MonHoc> listMonhoc = (List<MonHoc>) monHocService.getAllMonHoc();
		List<Lop> listLop = (List<Lop>) lopService.getAllLop();
		List<TaiKhoan> listTaiKhoan =taiKhoanService.top10TaiKhoan();
		
		if(dsDethi.size()==0 && page >1) {
			pageDethi = dethiService.findPaginated(page-1, MyConstances.HOMEPAGE_SIZE);
			dsDethi = pageDethi.getContent();
		}
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", pageDethi.getTotalPages());
		model.addAttribute("totalItems", pageDethi.getTotalElements());
		model.addAttribute("dsDeThi",dsDethi);
		model.addAttribute("listLop", listLop);
		model.addAttribute("listMonHoc", listMonhoc);
		model.addAttribute("listTaiKhoan",listTaiKhoan);
		return "user/thi/listExam";
	}
	
	/**
	 * Tìm kiếm đề thi
	 *
	 * @param keyword the keyword
	 * @param req the req
	 * @param model the model
	 * @param page the page
	 * @return the model and view
	 */
	@PostMapping(value = "/thi/page/{page}")
	public ModelAndView searchDeThi(@Param("keyword") String keyword,HttpServletRequest req, Model model,@PathVariable(name = "page",required = false) Integer page) {
		ModelAndView mav = new ModelAndView();
		List<DeThi> dsDethi;
		if(keyword.equals("")) {
			 dsDethi =(List<DeThi>) dethiService.findAllDeThi();
		}else {
			 dsDethi =(List<DeThi>) dethiService.findByTenDeContaining(keyword);
		}
		ShowFormFormListExam(mav);
		mav.addObject("dsDeThi",dsDethi);
		return mav;
	}
	
	private void ShowFormFormListExam(ModelAndView mav) {
		Optional<Role> role = roleService.findByRoleName(MyConstances.ROLE_USER);
		List<TaiKhoan> Top6TaiKhoan = taiKhoanService.findTop6UserMaxPoint(role.get());
		mav.addObject("TopUser",Top6TaiKhoan);
		List<MonHoc> listMonhoc = (List<MonHoc>) monHocService.getAllMonHoc();
		List<Lop> listLop = (List<Lop>) lopService.getAllLop();
		mav.addObject("listLop", listLop);
		mav.addObject("listMonHoc", listMonhoc);
		mav.setViewName("/user/thi/listExam");
	}

	/**
	 * Bắt đầu cuộc thi
	 *
	 * @param idDe the id de
	 * @return the model and view
	 */
	@RequestMapping(value="/thi/{idDe}",method = RequestMethod.GET)
	ModelAndView StartExam(@PathVariable int idDe,HttpServletRequest req) {
		HttpSession session = req.getSession();
		TaiKhoan taiKhoan = null;
		DeThi deThi = dethiService.findById(idDe).get();
		if(session.getAttribute("user")!=null) {
			if(session.getAttribute("baiDangThi")==null) {
				taiKhoan = (TaiKhoan) session.getAttribute("user");
				ThamGiaThi baiDangThi = thiService.batDauThi(taiKhoan, deThi);
				session.setAttribute("baiDangThi", baiDangThi);
			}
			
			ModelAndView mav = new ModelAndView();
			List<CauHoi> listCauHoi = cauHoiService.findAllByIdDeThi(idDe);
			
			mav.addObject("deThi", deThi);
			mav.addObject("thoiGian", deThi.getThoiGianThi());
			mav.addObject("listCauHoi", listCauHoi);
			mav.setViewName("/user/thi/startExam");
			return mav;
		}
		else {
			return new ModelAndView("404");
		}
		
		
	}
	
	@PostMapping(value="/thi/hoanThanh/{idDe}")
	@ResponseBody
	public ResponseEntity<?> hoanThanhBaiThi(@PathVariable(name = "idDe") Integer idDe,
			@RequestBody List<CauHoi> listCauHoi, HttpServletRequest req) {
		//List<CauHoi> listCauHoiDB = cauHoiService.findAllByIdDeThi(idDe);
		DeThi deThi = dethiService.findById(idDe).get();
		int totalQuestions = listCauHoi.size();
		int totalCorrectedQuestions = 0;
		for(int i =0;i<listCauHoi.size();i++) {
			if(cauHoiService.isCorrectAnswer(listCauHoi.get(i))) {
				totalCorrectedQuestions+=1;
			}
		}
		
		KetQuaBaiThi kq = new KetQuaBaiThi();
		kq.setSoCauDung(totalCorrectedQuestions);
		kq.setTongSoCau(totalQuestions);
		kq.setMaDe(deThi.getIdDe());
		kq.setTenDe(deThi.getTenDe());
		kq.setMonHoc(deThi.getMonHoc().getTenMonHoc());
		kq.setTenLop(deThi.getLop().getTenLop());
		kq.setThoiGianThi(deThi.getThoiGianThi());
		kq.getTongDiem();
		
		//Lưu vào db
		HttpSession session = req.getSession();
		TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("user");
		
		if(session.getAttribute("baiDangThi")==null||session.getAttribute("baiDangThi")=="") {
			thiService.batDauThi(taiKhoan, deThi);
		}
		if(session.getAttribute("baiDangThi")!=null) {
			ThamGiaThi baiDangThi = (ThamGiaThi) session.getAttribute("baiDangThi");
			thiService.hoanThanhBaiThi(baiDangThi, kq.getDiemSo(),taiKhoan.getUsername());
			session.removeAttribute("baiDangThi");
		}
		
		return	ResponseEntity.ok(kq);
	}

	
	@RequestMapping(value = "/findByMonHoc/{idMonHoc}")
	ModelAndView findByMonHoc(HttpServletRequest req,@PathVariable int idMonHoc,@Param("keyword") String keyword) {
		ModelAndView mav = new ModelAndView();
		List<DeThi> dsDethi=null;
		Optional<MonHoc> monHoc = monHocService.FindById(idMonHoc);
		if(monHoc.isPresent()) {
			dsDethi =(List<DeThi>) dethiService.findByMonHoc(monHoc.get());
		}
		if(keyword!=null) 
			dsDethi =dethiService.filterByKeyword(keyword, dsDethi);
		mav.addObject("dsDeThi",dsDethi);
		ShowFormFormListExam(mav);
		return mav;
		
	}
	@RequestMapping(value = "/findByLop/{idLopHoc}")
	ModelAndView findByLopHoc(HttpServletRequest req,@PathVariable int idLopHoc,@Param("keyword") String keyword) {
		ModelAndView mav = new ModelAndView();
		List<DeThi> dsDethi=null;
		Optional<Lop> lop = lopService.findByID(idLopHoc);
		if(lop.isPresent()) {
			dsDethi =(List<DeThi>) dethiService.findByLop(lop.get());
		}
		if(keyword!=null) 
			dsDethi =dethiService.filterByKeyword(keyword, dsDethi);
		mav.addObject("dsDeThi",dsDethi);
		ShowFormFormListExam(mav);
		return mav;
		
	}
	

}
