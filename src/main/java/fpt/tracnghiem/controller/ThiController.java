package fpt.tracnghiem.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fpt.tracnghiem.config.MyConstances;
import fpt.tracnghiem.entity.CauHoi;
import fpt.tracnghiem.entity.DeThi;
import fpt.tracnghiem.entity.Lop;
import fpt.tracnghiem.entity.MonHoc;
import fpt.tracnghiem.entity.TaiKhoan;
import fpt.tracnghiem.entity.ThamGiaThi;
import fpt.tracnghiem.service.CauHoiService;
import fpt.tracnghiem.service.DeThiService;
import fpt.tracnghiem.service.LopService;
import fpt.tracnghiem.service.MonHocService;
import fpt.tracnghiem.service.ThamGiaThiService;

@Controller
@RequestMapping(value = "/user")
public class ThiController {
	@Autowired
	private CauHoiService cauHoiService;
	@Autowired
	private DeThiService dethiService;
	@Autowired
	private ThamGiaThiService thamGiaThiService;

	@Autowired
	private LopService lopService;
	@Autowired
	private MonHocService monHocService;
	
	
	@RequestMapping(value = "/thi/page/{page}",method = RequestMethod.GET)
	public String userInfo(HttpServletRequest req, Model model,@PathVariable(name = "page",required = false) Integer page) {
		if(page == null || page < 1) {
			page=1;
		}
		Page<DeThi> pageDethi = dethiService.findPaginated(page, MyConstances.HOMEPAGE_SIZE);
		List<DeThi> dsDethi = pageDethi.getContent();
		List<MonHoc> listMonhoc = (List<MonHoc>) monHocService.getAllMonHoc();
		List<Lop> listLop = (List<Lop>) lopService.getAllLop();
		
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
		return "user/thi/listExam";
	}
	
	@PostMapping(value = "/thi/page/{page}")
	public ModelAndView searchDeThi(@Param("keyword") String keyword,HttpServletRequest req, Model model,@PathVariable(name = "page",required = false) Integer page) {
		ModelAndView mav = new ModelAndView();
		List<DeThi> dsDethi;
		if(keyword.equals("")) {
			 dsDethi =(List<DeThi>) dethiService.findAllDeThi();
		}else {
			 dsDethi =(List<DeThi>) dethiService.findByTenDeContaining(keyword);
		}
	
		mav.addObject("dsDeThi",dsDethi);
		ShowGiaoDienMenuThi(mav);
		return mav;
	}
	
	
	@RequestMapping(value = "/FindByLop/{idLop}")
	public ModelAndView searchDeThiByLop(@Param("keyword") String keyword,HttpServletRequest req, Model model,@PathVariable(name = "idLop",required = false) Integer idLop) {
		ModelAndView mav = new ModelAndView();
		List<DeThi> dsDethi = null;
		Optional<Lop> lop = lopService.findById(idLop);
		if(lop.isPresent()) {
			dsDethi = dethiService.findByLop(lop.get());
			
		}
		if(keyword!=null)
		if(keyword.equals("")) {
			 dsDethi =(List<DeThi>) dethiService.findAllDeThi();
		}else {
			 dsDethi =(List<DeThi>) dethiService.findByTenDeContaining(keyword);
		}
		mav.addObject("dsDeThi",dsDethi);
		
		ShowGiaoDienMenuThi(mav);
		return mav;
	}
	@RequestMapping(value = "/FindByMonHoc/{idMonHoc}")
	public ModelAndView searchDeThiByMonHoc(@Param("keyword") String keyword,HttpServletRequest req, Model model,@PathVariable(name = "idMonHoc",required = false) Integer idMonHoc) {
		ModelAndView mav = new ModelAndView();
		List<DeThi> dsDethi = null;
		Optional<MonHoc> MonHoc = monHocService.findById(idMonHoc);
		if(MonHoc.isPresent()) {
			dsDethi = dethiService.findByMonHoc(MonHoc.get());
			
		}
		if(keyword!=null)
		if(keyword.equals("")) {
			 dsDethi =(List<DeThi>) dethiService.findAllDeThi();
		}else {
			 dsDethi =(List<DeThi>) dethiService.findByTenDeContaining(keyword);
		}
		mav.addObject("dsDeThi",dsDethi);
		
		ShowGiaoDienMenuThi(mav);
		return mav;
	}
	
	
	@RequestMapping(value="/thi/{idDe}")
	public String OpenExam(@PathVariable int idDe,HttpServletRequest request, HttpServletResponse response) {
		
	
		Optional<DeThi> deThi = dethiService.findById(idDe);
		if(deThi.isPresent()) {
			
		}
		Calendar now = Calendar.getInstance();
		long timeInSecs = now.getTimeInMillis();
		Date TimePlus = new Date(timeInSecs + (deThi.get().getThoiGianThi() * 60 * 1000));
		ThamGiaThi thamGiaThi = new ThamGiaThi();
		HttpSession session = request.getSession();
		TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("user");
		Timestamp NgayGioBatDau = new Timestamp(now.getTimeInMillis());
		Timestamp NgayGioKetThuc = new Timestamp(TimePlus.getTime());
		
		
		thamGiaThi.setNgayGioBatDau(NgayGioBatDau);
		thamGiaThi.setNgayGioKetThuc(NgayGioKetThuc);
		thamGiaThi.setTaiKhoan(taiKhoan);
		thamGiaThi.setDeThi(deThi.get());
		thamGiaThi.setTongDiem(0);
		session.setAttribute("ThamGiaThi", thamGiaThi);
		thamGiaThiService.AddThamGiaThi(thamGiaThi);
		
		return "redirect:/user/thamgiathi/"+idDe;
	}
	@GetMapping(value="/thamgiathi/{idDe}")
	public ModelAndView StartExam(@PathVariable int idDe,HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView();
		List<CauHoi> listCauHoi = cauHoiService.findAllByIdDeThi(idDe);
		Optional<DeThi> deThi = dethiService.findById(idDe);
		if(deThi.isPresent()) {
			mav.addObject("deThi", deThi.get());
			mav.addObject("thoiGian", deThi.get().getThoiGianThi());
		}
		HttpSession session = request.getSession();
		
		if(session.getAttribute("ThamGiaThi")!=null) {
			ThamGiaThi thamGiaThi =(ThamGiaThi) session.getAttribute("ThamGiaThi");
			Timestamp NgayGioBatDau = thamGiaThi.getNgayGioBatDau();
			Timestamp NgayGioKetThuc = thamGiaThi.getNgayGioKetThuc();
			mav.addObject("timeStart", NgayGioBatDau);
			mav.addObject("timeEnd", NgayGioKetThuc);
		}
		mav.addObject("listCauHoi", listCauHoi);
		mav.setViewName("/user/thi/startExam");
		return mav;
	}

	private void ShowGiaoDienMenuThi(ModelAndView mav) {
		List<MonHoc> listMonhoc = (List<MonHoc>) monHocService.getAllMonHoc();
		List<Lop> listLop = (List<Lop>) lopService.getAllLop();
		mav.addObject("listLop", listLop);
		mav.addObject("listMonHoc", listMonhoc);
		mav.setViewName("user/thi/listExam");
	}

	
}
