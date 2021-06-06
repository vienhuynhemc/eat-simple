package com.vientamthuong.eatsimple.admin.loadData;

public class LoadData {

    private static LoadData loadData;
    // Biến để xem thông báo nổi sẵn sàng hay chưa ?
    private boolean isReadyFromThongBaoNoi;
    // Main fragment có sẵn sàn hay chưa
    private boolean isReadyFromMainFragment;

    private LoadData() {
    }

    public void reset() {
        isReadyFromThongBaoNoi = false;
        isReadyFromMainFragment = false;
    }

    public static LoadData getInstance() {
        if (loadData == null) {
            loadData = new LoadData();
        }
        return loadData;
    }

    public static LoadData getLoadData() {
        return loadData;
    }

    public static void setLoadData(LoadData loadData) {
        LoadData.loadData = loadData;
    }

    public boolean isReadyFromThongBaoNoi() {
        return isReadyFromThongBaoNoi;
    }

    public void setReadyFromThongBaoNoi(boolean readyFromThongBaoNoi) {
        isReadyFromThongBaoNoi = readyFromThongBaoNoi;
    }

    public boolean isReadyFromMainFragment() {
        return isReadyFromMainFragment;
    }

    public void setReadyFromMainFragment(boolean readyFromMainFragment) {
        isReadyFromMainFragment = readyFromMainFragment;
    }
}
