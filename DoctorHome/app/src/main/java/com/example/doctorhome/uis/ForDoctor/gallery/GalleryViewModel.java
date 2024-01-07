package com.example.doctorhome.uis.ForDoctor.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class GalleryViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<String>> informations = new MutableLiveData<>();

    public GalleryViewModel() {
        ArrayList<String> innitIfomations = generateInfo();
        informations.setValue(innitIfomations);
    }
    public LiveData<ArrayList<String>> getInfomations() {
        return informations;
    }

    private ArrayList<String> generateInfo() {
        ArrayList<String> infor = new ArrayList<>();
        infor.add("Bệnh viện đa khoa huyện Đan Phượng Thông báo: " +
                "Thông báo số 1145/TB-BVĐP ngày 30/11/2022 của Bệnh viện đa khoa huyện Đan Phượng Về việc: " +
                "Kiểm tra dữ liệu đăng ký tuyển dụng viên chức năm 2022 của Bệnh viện đa khoa huyện Đan Phượng.");
        infor.add("Bệnh viện Đan Phượng mời các đơn vị báo giá tư vấn đấu thầu\n" +
                "YÊU CẦU BÁO GIÁ Kính gửi: Các Hãng sản xuất, Nhà cung cấp dịch vụ tại Việt Nam\n" +
                "Bệnh viện đa khoa huyện Đan Phượng xin gửi Thư mời báo giá mua sắm hóa chất nội dung như tệp đính kèm");
        return infor;
    }
}