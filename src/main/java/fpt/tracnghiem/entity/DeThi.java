package fpt.tracnghiem.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Nationalized;

import java.util.List;


/**
 * The persistent class for the de_thi database table.
 * 
 */
@Entity
@Table(name="de_thi")
@NamedQuery(name="DeThi.findAll", query="SELECT d FROM DeThi d")
public class DeThi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_bo_de")
	private int idBoDe;

	@Column(name="id_ngan_hang_de")
	private int idNganHangDe;

	@Column(name="ten_bo_de")
	@Nationalized
	private String tenBoDe;

	//bi-directional many-to-one association to CauHoi
	@OneToMany(mappedBy="deThi")
	private List<CauHoi> cauHois;

	//bi-directional many-to-one association to Lop
	@ManyToOne
	@JoinColumn(name="id_lop")
	private Lop lop;

	//bi-directional many-to-one association to MonHoc
	@ManyToOne
	@JoinColumn(name="id_mon_hoc")
	private MonHoc monHoc;

	//bi-directional many-to-one association to TaiKhoan
	@ManyToOne
	@JoinColumn(name="username_nguoi_tao")
	private TaiKhoan taiKhoan;

	//bi-directional many-to-one association to ThamGiaThi
	@OneToMany(mappedBy="deThi")
	private List<ThamGiaThi> thamGiaThis;

	public DeThi() {
	}

	public int getIdBoDe() {
		return this.idBoDe;
	}

	public void setIdBoDe(int idBoDe) {
		this.idBoDe = idBoDe;
	}

	public int getIdNganHangDe() {
		return this.idNganHangDe;
	}

	public void setIdNganHangDe(int idNganHangDe) {
		this.idNganHangDe = idNganHangDe;
	}

	public String getTenBoDe() {
		return this.tenBoDe;
	}

	public void setTenBoDe(String tenBoDe) {
		this.tenBoDe = tenBoDe;
	}

	public List<CauHoi> getCauHois() {
		return this.cauHois;
	}

	public void setCauHois(List<CauHoi> cauHois) {
		this.cauHois = cauHois;
	}

	public CauHoi addCauHoi(CauHoi cauHoi) {
		getCauHois().add(cauHoi);
		cauHoi.setDeThi(this);

		return cauHoi;
	}

	public CauHoi removeCauHoi(CauHoi cauHoi) {
		getCauHois().remove(cauHoi);
		cauHoi.setDeThi(null);

		return cauHoi;
	}

	public Lop getLop() {
		return this.lop;
	}

	public void setLop(Lop lop) {
		this.lop = lop;
	}

	public MonHoc getMonHoc() {
		return this.monHoc;
	}

	public void setMonHoc(MonHoc monHoc) {
		this.monHoc = monHoc;
	}

	public TaiKhoan getTaiKhoan() {
		return this.taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public List<ThamGiaThi> getThamGiaThis() {
		return this.thamGiaThis;
	}

	public void setThamGiaThis(List<ThamGiaThi> thamGiaThis) {
		this.thamGiaThis = thamGiaThis;
	}

	public ThamGiaThi addThamGiaThi(ThamGiaThi thamGiaThi) {
		getThamGiaThis().add(thamGiaThi);
		thamGiaThi.setDeThi(this);

		return thamGiaThi;
	}

	public ThamGiaThi removeThamGiaThi(ThamGiaThi thamGiaThi) {
		getThamGiaThis().remove(thamGiaThi);
		thamGiaThi.setDeThi(null);

		return thamGiaThi;
	}

}