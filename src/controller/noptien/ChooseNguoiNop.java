package controller.noptien;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.KhoanThuModel;
import models.HoKhauModel;
import models.QuanHeModel;
import services.HoKhauService;
import services.QuanHeService;

public class ChooseNguoiNop implements Initializable {
	@FXML
	private TableColumn<HoKhauModel, String> colMaHoKhau;
	@FXML
	private TableColumn<HoKhauModel, String> colTenChuHo;
	@FXML
	private TableColumn<HoKhauModel, String> colSoThanhVien;
	@FXML
	private TableColumn<HoKhauModel, String> colDiaChi;
	@FXML
	private TableColumn<HoKhauModel, String> colChuaNopTien;
	@FXML
	private TableView<HoKhauModel> tvHoKhau;
	@FXML
	private TextField tfSearch;
	@FXML
	private ComboBox<String> cbChooseSearch;

	private HoKhauModel hoKhauChoose;
	
	public HoKhauModel getNhanKhauChoose() {
		return hoKhauChoose;
	}

	public void setHoKhauChoose(HoKhauModel hoKhauChoose) {
		this.hoKhauChoose = hoKhauChoose;
	}

	private ObservableList<HoKhauModel> listValueTableView;
	private List<HoKhauModel> listHoKhau;

	public void showHoKhau() throws ClassNotFoundException, SQLException {
		listHoKhau = new HoKhauService().getListHoKhau();
		listValueTableView = FXCollections.observableArrayList(listHoKhau);

		// tao map anh xa gia tri Id sang maHo
		Map<Integer, Integer> mapIdToMaho = new HashMap<>();
		List<QuanHeModel> listQuanHe = new QuanHeService().getListQuanHe();
		listQuanHe.forEach(quanhe -> {
			mapIdToMaho.put(quanhe.getIdThanhVien(), quanhe.getMaHo());
		});

		// thiet lap cac cot cho tableviews
		colMaHoKhau.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("maHo"));
		colSoThanhVien.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("soThanhvien"));
		colDiaChi.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("diaChi"));
		colChuaNopTien.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("checkNoptien"));
		colTenChuHo.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("tenChuHo"));
//		try {
//			colMaHo.setCellValueFactory((CellDataFeatures<HoKhauModel, String> p) -> new ReadOnlyStringWrapper(
//					mapIdToMaho.get(p.getValue().getId()).toString()));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		tvHoKhau.setItems(listValueTableView);

		// thiet lap gia tri cho combobox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("M?? h??? kh???u", "?????a ch???", "Kho???n ???? n???p");
		cbChooseSearch.setValue("?????a ch???");
		cbChooseSearch.setItems(listComboBox);
	}

	public void searchNhanKhau() {
		ObservableList<HoKhauModel> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// tim kiem thong tin theo lua chon da lay ra
		switch (typeSearchString) {
		case "?????a ch???": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvHoKhau.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "H??y nh???p v??o th??ng tin c???n t??m ki???m!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int index = 0;
			List<HoKhauModel> listHoKhauModelsSearch = new ArrayList<>();
			for (HoKhauModel HoKhauModel : listHoKhau) {
				if (HoKhauModel.getDiaChi().contains(keySearch)) {
					listHoKhauModelsSearch.add(HoKhauModel);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listHoKhauModelsSearch);
			tvHoKhau.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
			// tim thay
			if (index == 0) {
				tvHoKhau.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Kh??ng t??m th???y th??ng tin!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		case "Kho???n ???? n???p": {
			// neu khong nhap gi -> thong bao loi
						if (keySearch.length() == 0) {
							tvHoKhau.setItems(listValueTableView);
							Alert alert = new Alert(AlertType.WARNING, "H??y nh???p v??o th??ng tin c???n t??m ki???m!", ButtonType.OK);
							alert.setHeaderText(null);
							alert.showAndWait();
							break;
						}

						int index = 0;
						List<HoKhauModel> listHoKhauModelsSearch = new ArrayList<>();
						for (HoKhauModel HoKhauModel : listHoKhau) {
							if (HoKhauModel.getCheckNoptien().contains(keySearch)) {
								listHoKhauModelsSearch.add(HoKhauModel);
								index++;
							}
						}
						listValueTableView_tmp = FXCollections.observableArrayList(listHoKhauModelsSearch);
						tvHoKhau.setItems(listValueTableView_tmp);

						// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
						// tim thay
						if (index == 0) {
							tvHoKhau.setItems(listValueTableView); // hien thi toan bo thong tin
							Alert alert = new Alert(AlertType.INFORMATION, "Kh??ng t??m th???y th??ng tin!", ButtonType.OK);
							alert.setHeaderText(null);
							alert.showAndWait();
						}
						break;
					}
		case "M?? h??? kh???u": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvHoKhau.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "H??y nh???p v??o th??ng tin c???n t??m ki???m!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			// kiem tra chuoi nhap vao co phai la chuoi hop le hay khong
			Pattern pattern = Pattern.compile("\\d{1,}");
			if (!pattern.matcher(keySearch).matches()) {
				Alert alert = new Alert(AlertType.WARNING, "M?? h??? kh???u nh???p v??o ph???i l?? 1 s???!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int index = 0;
			List<HoKhauModel> listHoKhau_tmp = new ArrayList<>();
			for (HoKhauModel hoKhauModel : listHoKhau) {
				if (hoKhauModel.getMaHo() == Integer.parseInt(keySearch)) {
					listHoKhau_tmp.add(hoKhauModel);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listHoKhau_tmp);
			tvHoKhau.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin tim kiem -> thong bao toi nguoi dung
			if (index == 0) {
				tvHoKhau.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Kh??ng t??m th???y th??ng tin!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		default: 
	}
}

	public void xacnhan(ActionEvent event) {
		hoKhauChoose = tvHoKhau.getSelectionModel().getSelectedItem();
		setHoKhauChoose(hoKhauChoose);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showHoKhau();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
