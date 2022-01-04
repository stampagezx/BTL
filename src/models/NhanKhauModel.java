package models;

public class NhanKhauModel {
	int id;
	String cmnd;
	String ten;
	int tuoi;
	String sdt;
	String trangthai;
	public int getMaho() {
		return maho;
	}

	public void setMaho(int maho) {
		this.maho = maho;
	}

	int maho;
	
	public NhanKhauModel() {}
	
	public NhanKhauModel(String cmnd, String ten, int tuoi , String sdt, String trangthai, int maho) {
		this.cmnd = cmnd;
		this.ten=ten;
		this.tuoi=tuoi;
		this.sdt = sdt;
		this.trangthai = trangthai;
		this.maho = maho;
	}
	
	public NhanKhauModel(int id,String cmnd, String ten, int tuoi , String sdt, String trangthai, int maho) {
		this.id=id;
		this.cmnd = cmnd;
		this.ten=ten;
		this.tuoi=tuoi;
		this.sdt = sdt;
		this.trangthai = trangthai;
		this.maho = maho;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public int getTuoi() {
		return tuoi;
	}

	public void setTuoi(int tuoi) {
		this.tuoi = tuoi;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	
	
}
