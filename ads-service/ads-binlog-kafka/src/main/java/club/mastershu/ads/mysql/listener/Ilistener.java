package club.mastershu.ads.mysql.listener;

import club.mastershu.ads.dto.BinlogRowData;

public interface Ilistener {
    void register();

    void onEvent(BinlogRowData eventData);
}
