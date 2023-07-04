package vn.funix.fx21678.java.asm04.utils;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckCustomerIdUtils {
    private static Map<String, String> mapMaTinh = new HashMap<>();
    public static void setMaTinh() {
        mapMaTinh.put("001", "Ha Noi");
        mapMaTinh.put("002", "Ha Giang");
        mapMaTinh.put("004", "Cao Bang");
        mapMaTinh.put("006", "Bac Kan");
        mapMaTinh.put("008", "Tuyen Quang");
        mapMaTinh.put("010", "Lao Cai");
        mapMaTinh.put("011", "Dien Bien");
        mapMaTinh.put("012", "Lai Chau");
        mapMaTinh.put("014", "Son La");
        mapMaTinh.put("015", "Yen Bai");
        mapMaTinh.put("017", "Hoa Binh");
        mapMaTinh.put("019", "Thai Nguyen");
        mapMaTinh.put("020", "Son La");
        mapMaTinh.put("022", "Quang Ninh");
        mapMaTinh.put("024", "Bac Giang");
        mapMaTinh.put("025", "Phu Tho");
        mapMaTinh.put("026", "Vinh Phuc");
        mapMaTinh.put("027", "Bac Ninh");
        mapMaTinh.put("030", "Hai Duong");
        mapMaTinh.put("031", "Hai Phong");
        mapMaTinh.put("033", "Hung Yen");
        mapMaTinh.put("034", "Thai Binh");
        mapMaTinh.put("035", "Ha Nam");
        mapMaTinh.put("036", "Nam Dinh");
        mapMaTinh.put("037", "Ninh Binh");
        mapMaTinh.put("038", "Thanh Hoa");
        mapMaTinh.put("040", "Nghe Aa");
        mapMaTinh.put("042", "Ha Tinh");
        mapMaTinh.put("044", "Quang Binh");
        mapMaTinh.put("045", "Quang Tri");
        mapMaTinh.put("046", "Thua Thien Hue");
        mapMaTinh.put("048", "Da Nang");
        mapMaTinh.put("049", "Quang Nam");
        mapMaTinh.put("051", "Quang Ngai");
        mapMaTinh.put("052", "Binh Dinh");
        mapMaTinh.put("054", "Phu Yen");
        mapMaTinh.put("056", "Khanh Hoa");
        mapMaTinh.put("058", "Ninh Thuan");
        mapMaTinh.put("060", "Binh Thuan");
        mapMaTinh.put("062", "Kon Tum");
        mapMaTinh.put("064", "Gia Lai");
        mapMaTinh.put("066", "Dak Lak");
        mapMaTinh.put("067", "Dak Nong");
        mapMaTinh.put("068", "Lam Dong");
        mapMaTinh.put("070", "Binh Phuoc");
        mapMaTinh.put("072", "Tay Ninh");
        mapMaTinh.put("074", "Binh Duong");
        mapMaTinh.put("075", "Dong Nai");
        mapMaTinh.put("077", "Ba Ria-Vung Tau");
        mapMaTinh.put("079", "Ho Chi Minh");
        mapMaTinh.put("080", "Long An");
        mapMaTinh.put("082", "Tien Giang");
        mapMaTinh.put("083", "Ben Tre");
        mapMaTinh.put("084", "Tra Vinh");
        mapMaTinh.put("086", "Vinh Long");
        mapMaTinh.put("087", "Dong Thap");
        mapMaTinh.put("089", "An Giang");
        mapMaTinh.put("091", "Kien Giang");
        mapMaTinh.put("092", "Can Tho");
        mapMaTinh.put("093", "Hau Giang");
        mapMaTinh.put("094", "Soc Trang");
        mapMaTinh.put("095", "Bac Lieu");
        mapMaTinh.put("096", "Cau Mau");

    }


    public static boolean checkInt(String string) {
        try {
            // check xem co ki tu nào khác số ko
            String[] cccds = string.split("");
            for (int i = 0; i < cccds.length; i++) {
                int so = Integer.parseInt(cccds[i]);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkCCCD(String cccd) {
        if (!checkInt(cccd))
            return false;
        if (cccd.length() != 12) {
            return false;
        }

        mapMaTinh.clear();
        setMaTinh();

        List<String> listMaTinh = new ArrayList<>();
        for (Map.Entry<String, String> map : mapMaTinh.entrySet()) {
            listMaTinh.add(map.getKey());
        }
        List<String> listGioiTinh = new ArrayList<>();
        listGioiTinh.add("0");
        listGioiTinh.add("1");
        listGioiTinh.add("2");
        listGioiTinh.add("3");



        String[] cccds = cccd.split("");
        StringBuilder maTinhBuilder = new StringBuilder();
        maTinhBuilder.append(cccds[0]).append(cccds[1]).append(cccds[2]);
        String maTinh = maTinhBuilder.toString();

        String maGioiTinh = cccds[3];

        StringBuilder maNamsinhBuilder = new StringBuilder();
        maNamsinhBuilder.append(cccds[4]).append(cccds[5]);
        String maNamSinh = maNamsinhBuilder.toString();
        String nam = "20";
        if (maGioiTinh.equals("2") || maGioiTinh.equals("3")) {
            nam += maNamSinh;
        }
        int namSinh = Integer.parseInt(nam);
        int namHienTai = Year.now().getValue();

        if (!listGioiTinh.contains(maGioiTinh)) {
            return false;
        }
        if (namSinh > namHienTai) {
            return false;
        }
        if (!listMaTinh.contains(maTinh)) {
            return false;
        }
        return true;
    }


}
